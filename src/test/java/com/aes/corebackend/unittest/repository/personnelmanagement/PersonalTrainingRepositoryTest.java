package com.aes.corebackend.unittest.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.repository.personnelmanagement.PersonalTrainingRepository;
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
public class PersonalTrainingRepositoryTest {
    @Autowired
    private PersonalTrainingRepository personalTrainingRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testCreatePersonalTrainingInformationSuccess() {

        User user = userRepository.getById(1L);
        PersonalTrainingInfo personalTrainingInfo = PersonalTrainingInfo.builder()
                .id(3L)
                .programName("Java")
                .trainingInstitute("coursera")
                .description("description")
                //TODO date validation end date > start date
                .startDate(LocalDate.of(2020, 1, 4))
                .endDate(LocalDate.of(2020, 1, 4))
                .user(user)
                .build();

        personalTrainingRepository.save(personalTrainingInfo);
        Assertions.assertThat(personalTrainingRepository.getById(3L).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testUpdatePersonalTrainingInformationSuccess() {

        PersonalTrainingInfo existingPersonalTrainingInfo = personalTrainingRepository.getById(1L);
        existingPersonalTrainingInfo.setProgramName("Python");
        personalTrainingRepository.save(existingPersonalTrainingInfo);

        Assertions.assertThat(personalTrainingRepository.getById(1L).getProgramName()).isEqualTo("Python");
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalTrainingInformationListByUserIdSuccess() {

        ArrayList<PersonalTrainingInfo> trainingList = personalTrainingRepository.findPersonalTrainingInfosByUserId(1L);

        Assertions.assertThat(trainingList.size()).isGreaterThan(0);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalTrainingInformationListByUserIdNoRecordFound() {

        ArrayList<PersonalTrainingInfo> trainingList = personalTrainingRepository.findPersonalTrainingInfosByUserId(100L);

        Assertions.assertThat(trainingList.size()).isEqualTo(0);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindPersonalTrainingInformationByIdAndUserIdSuccess() {

        PersonalTrainingInfo personalTrainingInfo = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(1L, 1L);

        Assertions.assertThat(personalTrainingInfo).isNotNull();
        Assertions.assertThat(personalTrainingInfo.getId()).isEqualTo(1L);
        Assertions.assertThat(personalTrainingInfo.getUser().getId()).isEqualTo(1L);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void testFindSinglePersonalTrainingInformationByIdAndUserIdNoRecordFound() {

        PersonalTrainingInfo personalTrainingInfo = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(100L, 1L);

        Assertions.assertThat(personalTrainingInfo).isNull();
    }
}
