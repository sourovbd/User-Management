package com.aes.corebackend.unit.test.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.repository.personnelmanagement.PersonalFamilyInfoRepository;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonalFamilyInfoRepositoryTest {

    @Autowired
    private PersonalFamilyInfoRepository personalFamilyInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_family_info.xml")
    public void testCreatePersonalFamilyInfoSuccess() {

        User user = userRepository.getById(2L);
        PersonalFamilyInfo personalFamilyInfo = PersonalFamilyInfo.builder()
                .id(2L)
                .fathersName("Mr. Test")
                .mothersName("Mrs. Test")
                .maritalStatus("Married")
                .spouseName("Test")
                .user(user)
                .build();

        System.out.println("===>" + personalFamilyInfo.toString());
        personalFamilyInfoRepository.save(personalFamilyInfo);

        Assertions.assertThat(personalFamilyInfoRepository.getById(2L).getFathersName()).isEqualTo(personalFamilyInfo.getFathersName());
        Assertions.assertThat(personalFamilyInfoRepository.getById(2L).getMothersName()).isEqualTo(personalFamilyInfo.getMothersName());
    }

    @Test
    @Order(2)
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_family_info.xml")
    public void testCreatePersonalFamilyInfoFailedDuplicateUserId() {

        /** this user has family information saved in DB,
         * saving another will generate constraint violation exception
         * because of one-to-one relation mapping
         * */
        User user = userRepository.getById(1L);
        PersonalFamilyInfo personalFamilyInfo = PersonalFamilyInfo.builder()
                .id(2L)
                .fathersName("Mr. Test")
                .mothersName("Mrs. Test")
                .maritalStatus("Married")
                .spouseName("Test")
                .user(user)
                .build();

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            personalFamilyInfoRepository.save(personalFamilyInfo);
        });

        String expectedMessage = "could not execute statement; SQL [n/a]; constraint";
        Assertions.assertThat(exception.getMessage().contains(expectedMessage)).isEqualTo(true);
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_family_info.xml")
    public void testUpdatePersonalFamilySuccess() {

        PersonalFamilyInfo existingFamilyInfo = personalFamilyInfoRepository.getById(1L);
        existingFamilyInfo.setMaritalStatus("Married");
        existingFamilyInfo.setSpouseName("Mrs. Doe");

        personalFamilyInfoRepository.save(existingFamilyInfo);

        Assertions.assertThat(personalFamilyInfoRepository.getById(1L).getMaritalStatus()).isEqualTo("Married");
        Assertions.assertThat(personalFamilyInfoRepository.getById(1L).getSpouseName()).isEqualTo("Mrs. Doe");
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_family_info.xml")
    public void testFindPersonalIdentificationByUserIdSuccess() {

        User existingUser = userRepository.getById(1L);
        PersonalFamilyInfo existingFamilyInfo = personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(existingUser.getId());

        Assertions.assertThat(existingFamilyInfo.getUser().getId()).isEqualTo(existingUser.getId());
        Assertions.assertThat(existingFamilyInfo.getUser().getEmployeeId()).isEqualTo(existingUser.getEmployeeId());
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/personal_family_info.xml")
    public void testFindPersonalIdentificationByUserIdNoRecordFound() {

        PersonalFamilyInfo existingFamilyInfo = personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(9999L);

        Assertions.assertThat(existingFamilyInfo).isNull();
    }
}
