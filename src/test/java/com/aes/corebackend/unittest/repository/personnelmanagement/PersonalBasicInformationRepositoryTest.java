package com.aes.corebackend.unittest.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.enumeration.Gender;
import com.aes.corebackend.repository.personnelmanagement.PersonalBasicInfoRepository;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class PersonalBasicInformationRepositoryTest {

    @Autowired
    private PersonalBasicInfoRepository personalBasicInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testCreatePersonalBasicInformationSuccess() {
        User user = userRepository.getById(2L);
        PersonalBasicInfo personalBasicInfo = PersonalBasicInfo.builder()
                .id(2L)
                .firstName("first")
                .lastName("last")
                .dateOfBirth(LocalDate.of(2020, 1, 4))
                .gender(Gender.MALE)
                .user(user)
                .build();

        personalBasicInfoRepository.save(personalBasicInfo);
        Assertions.assertThat(personalBasicInfoRepository.getById(2L).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testCreatePersonalBasicInformationFailedDuplicateUserId() {
        User user = userRepository.getById(1L);
        PersonalBasicInfo personalBasicInfo = PersonalBasicInfo.builder()
                .firstName("first")
                .lastName("last")
                .dateOfBirth(LocalDate.of(2020, 1, 4))
                .gender(Gender.MALE)
                .user(user)
                .build();

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            personalBasicInfoRepository.save(personalBasicInfo);
        });

        String expectedMessage = "could not execute statement; SQL [n/a]; constraint";
        Assertions.assertThat(exception.getMessage().contains(expectedMessage)).isEqualTo(true);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testUpdatePersonalBasicInformationSuccess() {

        PersonalBasicInfo existingPersonalBasicInfo = personalBasicInfoRepository.getById(1L);

        existingPersonalBasicInfo.setFirstName("firstname");
        existingPersonalBasicInfo.setLastName("lastname");
        personalBasicInfoRepository.save(existingPersonalBasicInfo);

        Assertions.assertThat(personalBasicInfoRepository.getById(1L).getFirstName()).isEqualTo("firstname");
        Assertions.assertThat(personalBasicInfoRepository.getById(1L).getLastName()).isEqualTo("lastname");
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalBasicInformationByUserIdSuccess() {

        User existingUser = userRepository.getById(1L);
        PersonalBasicInfo existingPersonalBasicInfo = personalBasicInfoRepository.findPersonalBasicInfoByUserId(existingUser.getId());

        Assertions.assertThat(existingPersonalBasicInfo.getUser().getId()).isEqualTo(existingUser.getId());
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalBasicInformationByUserIdNoRecordFound() {

        PersonalBasicInfo existingPersonalBasicInfo = personalBasicInfoRepository.findPersonalBasicInfoByUserId(100L);

        Assertions.assertThat(existingPersonalBasicInfo).isNull();
    }
}
