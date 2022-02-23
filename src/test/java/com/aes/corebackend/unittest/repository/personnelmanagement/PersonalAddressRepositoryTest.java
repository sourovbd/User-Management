package com.aes.corebackend.unittest.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.enumeration.Gender;
import com.aes.corebackend.repository.personnelmanagement.PersonalAddressInfoRepository;
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
public class PersonalAddressRepositoryTest {

    @Autowired
    private PersonalAddressInfoRepository personalAddressInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_address_info.xml")
    public void testCreatePersonalAddressInformationSuccess() {
        User user = userRepository.getById(2L);
        PersonalAddressInfo personalAddressInfo = PersonalAddressInfo.builder()
                .id(2L)
                .permanentAddress("CTG")
                .presentAddress("DHK")
                .user(user)
                .build();

        personalAddressInfoRepository.save(personalAddressInfo);
        Assertions.assertThat(personalAddressInfoRepository.getById(2L).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_address_info.xml")
    public void testCreatePersonalAddressInformationFailedDuplicateUserId() {
        User user = userRepository.getById(1L);
        PersonalAddressInfo personalAddressInfo = PersonalAddressInfo.builder()
                .id(2L)
                .permanentAddress("CTG")
                .presentAddress("DHK")
                .user(user)
                .build();

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            personalAddressInfoRepository.save(personalAddressInfo);
        });

        String expectedMessage = "could not execute statement; SQL [n/a]; constraint";
        Assertions.assertThat(exception.getMessage().contains(expectedMessage)).isEqualTo(true);
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_address_info.xml")
    public void testUpdatePersonalBasicInformationSuccess() {

        PersonalAddressInfo existingPersonalAddressInfo = personalAddressInfoRepository.getById(1L);

        existingPersonalAddressInfo.setPermanentAddress("ABC");
        personalAddressInfoRepository.save(existingPersonalAddressInfo);
        Assertions.assertThat(personalAddressInfoRepository.getById(1L).getPermanentAddress()).isEqualTo("ABC");
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_address_info.xml")
    public void testFindPersonalBasicInformationByUserIdSuccess() {

        User existingUser = userRepository.getById(1L);
        PersonalAddressInfo existingPersonalAddressInfo = personalAddressInfoRepository.findPersonalAddressInfoByUserId(existingUser.getId());

        Assertions.assertThat(existingPersonalAddressInfo.getUser().getId()).isEqualTo(existingUser.getId());
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_address_info.xml")
    public void testFindPersonalBasicInformationByUserIdNoRecordFound() {

        PersonalAddressInfo existingPersonalAddressInfo = personalAddressInfoRepository.findPersonalAddressInfoByUserId(100L);

        Assertions.assertThat(existingPersonalAddressInfo).isNull();
    }
}
