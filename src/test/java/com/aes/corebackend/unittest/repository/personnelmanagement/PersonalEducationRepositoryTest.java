package com.aes.corebackend.unittest.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.repository.personnelmanagement.PersonalEducationRepository;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class PersonalEducationRepositoryTest {

    @Autowired
    private PersonalEducationRepository personalEducationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testCreatePersonalEducationSuccess() {

        User user = userRepository.getById(1L);
        PersonalEducationInfo personalEducationInfo = PersonalEducationInfo.builder()
                .id(3L)
                .cgpa(3.50f)
                .degreeName("MS")
                .gpaScale(4.00f)
                .institutionName("Test University")
                .passingYear("2021")
                .user(user)
                .build();

        personalEducationRepository.save(personalEducationInfo);

        Assertions.assertThat(personalEducationRepository.getById(3L).getUser().getId()).isEqualTo(user.getId());
        Assertions.assertThat(personalEducationRepository.getById(3L).getDegreeName()).isEqualTo(personalEducationInfo.getDegreeName());
        Assertions.assertThat(personalEducationRepository.getById(3L).getInstitutionName()).isEqualTo(personalEducationInfo.getInstitutionName());
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testUpdatePersonalEducationSuccess() {

        PersonalEducationInfo existingEducationInfo = personalEducationRepository.getById(1L);
        existingEducationInfo.setCgpa(5.00f);
        existingEducationInfo.setInstitutionName("ABC High School");
        personalEducationRepository.save(existingEducationInfo);

        Assertions.assertThat(personalEducationRepository.getById(1L).getCgpa()).isEqualTo(5.00f);
        Assertions.assertThat(personalEducationRepository.getById(1L).getInstitutionName()).isEqualTo("ABC High School");
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testFindEducationListByUserIdSuccess() {

        ArrayList<PersonalEducationInfo> educationList = personalEducationRepository.findPersonalEducationInfoByUserId(1L);

        Assertions.assertThat(educationList.size()).isGreaterThan(0);
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testFindEducationListByUserIdNoRecordFound() {

        ArrayList<PersonalEducationInfo> educationList = personalEducationRepository.findPersonalEducationInfoByUserId(9999L);

        Assertions.assertThat(educationList.size()).isEqualTo(0);
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testFindSingleEducationByIdAndUserIdSuccess() {

        PersonalEducationInfo personalEducationInfo = personalEducationRepository.findPersonalEducationInfoByIdAndUserId(1L, 1L);

        Assertions.assertThat(personalEducationInfo).isNotNull();
        Assertions.assertThat(personalEducationInfo.getId()).isEqualTo(1L);
        Assertions.assertThat(personalEducationInfo.getUser().getId()).isEqualTo(1L);
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testFindSingleEducationByIdAndUserIdNoRecordFound() {

        PersonalEducationInfo personalEducationInfo = personalEducationRepository.findPersonalEducationInfoByIdAndUserId(9999L, 1L);

        Assertions.assertThat(personalEducationInfo).isNull();
    }
}
