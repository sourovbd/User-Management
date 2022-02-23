package com.aes.corebackend.unittest.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.repository.personnelmanagement.PersonalJobExperienceRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class PersonalJobExperienceRepositoryTest {

    @Autowired
    private PersonalJobExperienceRepository personalJobExperienceRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testCreatePersonalJobExperienceSuccess() {

        User user = userRepository.getById(1L);
        PersonalJobExperience personalJobExperience = PersonalJobExperience.builder()
                .id(3L)
                .employerName("employer")
                .designation("SDE")
                .startDate(LocalDate.of(2020, 1, 4))
                .endDate(LocalDate.of(2020, 1, 4))
                .responsibilities("responsibilities")
                .user(user)
                .build();
        personalJobExperienceRepository.save(personalJobExperience);
        Assertions.assertThat(personalJobExperienceRepository.getById(3L).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testUpdatePersonalJobExperienceSuccess() {

        PersonalJobExperience existingPersonalJobExperience = personalJobExperienceRepository.getById(1L);
        existingPersonalJobExperience.setEmployerName("newEmployer");
        personalJobExperienceRepository.save(existingPersonalJobExperience);

        Assertions.assertThat(personalJobExperienceRepository.getById(1L).getEmployerName()).isEqualTo("newEmployer");
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalJobExperienceListByUserIdSuccess() {

        ArrayList<PersonalJobExperience> jobExperienceList = personalJobExperienceRepository.findPersonalJobExperiencesByUserId(1L);

        Assertions.assertThat(jobExperienceList.size()).isGreaterThan(0);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalJobExperienceListByUserIdNoRecordFound() {

        ArrayList<PersonalJobExperience> jobExperienceList = personalJobExperienceRepository.findPersonalJobExperiencesByUserId(100L);

        Assertions.assertThat(jobExperienceList.size()).isEqualTo(0);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalJobExperienceByIdAndUserIdSuccess() {

        PersonalJobExperience personalJobExperience = personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(1L, 1L);

        Assertions.assertThat(personalJobExperience).isNotNull();
        Assertions.assertThat(personalJobExperience.getId()).isEqualTo(1L);
        Assertions.assertThat(personalJobExperience.getUser().getId()).isEqualTo(1L);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindSinglePersonalJobExperienceByIdAndUserIdNoRecordFound() {

        PersonalJobExperience personalJobExperience = personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(100L, 1L);

        Assertions.assertThat(personalJobExperience).isNull();
    }
}
