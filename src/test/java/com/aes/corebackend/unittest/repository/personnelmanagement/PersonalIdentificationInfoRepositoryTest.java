package com.aes.corebackend.unittest.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.repository.personnelmanagement.PersonalIdentificationInfoRepository;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class PersonalIdentificationInfoRepositoryTest {

    @Autowired
    private PersonalIdentificationInfoRepository personalIdentificationInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testCreatePersonalIdentificationSuccess() {

        User user = userRepository.getById(2L);
        PersonalIdentificationInfo personalIdentificationInfo = PersonalIdentificationInfo.builder()
                .id(2L)
                .etin("751983938444")
                .nationalID("1507359444")
                .user(user)
                .build();

        personalIdentificationInfoRepository.save(personalIdentificationInfo);

        Assertions.assertThat(personalIdentificationInfoRepository.getById(2L).getEtin()).isEqualTo(personalIdentificationInfo.getEtin());
        Assertions.assertThat(personalIdentificationInfoRepository.getById(2L).getNationalID()).isEqualTo(personalIdentificationInfo.getNationalID());
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testCreatePersonalIdentificationFailedDuplicateUserId() {

        /** this user has identification information saved in DB,
         * saving another will generate constraint violation exception
         * because of one-to-one relation mapping
         * */
        User user = userRepository.getById(1L);
        PersonalIdentificationInfo personalIdentificationInfo = PersonalIdentificationInfo.builder()
                .id(2L)
                .etin("751983938444")
                .nationalID("1507359444")
                .user(user)
                .build();

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            personalIdentificationInfoRepository.save(personalIdentificationInfo);
        });

        String expectedMessage = "could not execute statement; SQL [n/a]; constraint";
        Assertions.assertThat(exception.getMessage().contains(expectedMessage)).isEqualTo(true);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testUpdatePersonalIdentificationSuccess() {

        PersonalIdentificationInfo existingIdentificationInfo = personalIdentificationInfoRepository.getById(1L);
        existingIdentificationInfo.setEtin("222666999333");
        existingIdentificationInfo.setNationalID("1075344499");

        personalIdentificationInfoRepository.save(existingIdentificationInfo);

        Assertions.assertThat(personalIdentificationInfoRepository.getById(1L).getEtin()).isEqualTo("222666999333");
        Assertions.assertThat(personalIdentificationInfoRepository.getById(1L).getNationalID()).isEqualTo("1075344499");
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalIdentificationByUserIdSuccess() {

        User existingUser = userRepository.getById(1L);
        PersonalIdentificationInfo existingIdentificationInfo = personalIdentificationInfoRepository.findPersonalIdentificationInfoByUserId(existingUser.getId());

        Assertions.assertThat(existingIdentificationInfo.getUser().getId()).isEqualTo(existingUser.getId());
        Assertions.assertThat(existingIdentificationInfo.getUser().getEmployeeId()).isEqualTo(existingUser.getEmployeeId());
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalIdentificationByUserIdNoUserFound() {

        PersonalIdentificationInfo existingIdentificationInfo = personalIdentificationInfoRepository.findPersonalIdentificationInfoByUserId(9999L);

        Assertions.assertThat(existingIdentificationInfo).isNull();
    }
}
