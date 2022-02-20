package com.aes.corebackend.unittest.repository.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.repository.personnelmanagement.PersonalAttributesRepository;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class PersonalAttributesRepositoryTest {

    @Autowired
    private PersonalAttributesRepository personalAttributesRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testCreatePersonalAttributesSuccess() {

        User user = userRepository.getById(2L);
        PersonalAttributes personalAttributes = PersonalAttributes.builder()
                .id(2L)
                .bloodGroup("O-")
                .nationality("Bangladeshi")
                .birthPlace("Dhaka")
                .religion("Islam")
                .user(user)
                .build();

        personalAttributesRepository.save(personalAttributes);

        Assertions.assertThat(personalAttributesRepository.getById(2L).getUser().getId()).isEqualTo(user.getId());
        Assertions.assertThat(personalAttributesRepository.getById(2L).getBloodGroup()).isEqualTo(personalAttributes.getBloodGroup());
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testCreatePersonalAttributesFailedDuplicateUserId() {

        /** this user has attributes saved in DB,
         * saving another will generate constraint violation exception
         * because of one-to-one relation mapping
         * */
        User user = userRepository.getById(1L);
        PersonalAttributes personalAttributes = PersonalAttributes.builder()
                .id(2L)
                .bloodGroup("O-")
                .nationality("Bangladeshi")
                .birthPlace("Dhaka")
                .religion("Islam")
                .user(user)
                .build();

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            personalAttributesRepository.save(personalAttributes);
        });

        String expectedMessage = "could not execute statement; SQL [n/a]; constraint";
        Assertions.assertThat(exception.getMessage().contains(expectedMessage)).isEqualTo(true);
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testUpdatePersonalAttributesSuccess() {

        PersonalAttributes existingAttributes = personalAttributesRepository.getById(1L);
        existingAttributes.setBloodGroup("AB+");
        existingAttributes.setBirthPlace("New York City");
        existingAttributes.setNationality("American");

        personalAttributesRepository.save(existingAttributes);

        Assertions.assertThat(personalAttributesRepository.getById(1L).getBloodGroup()).isEqualTo("AB+");
        Assertions.assertThat(personalAttributesRepository.getById(1L).getBirthPlace()).isEqualTo("New York City");
        Assertions.assertThat(personalAttributesRepository.getById(1L).getNationality()).isEqualTo("American");
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testFindPersonalAttributesByUserIdSuccess() {

        User existingUser = userRepository.getById(1L);
        PersonalAttributes existingPersonalAttributes = personalAttributesRepository.findPersonalAttributesByUserId(existingUser.getId());

        Assertions.assertThat(existingPersonalAttributes.getUser().getId()).isEqualTo(existingUser.getId());
        Assertions.assertThat(existingPersonalAttributes.getUser().getEmployeeId()).isEqualTo(existingUser.getEmployeeId());
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void testFindPersonalAttributesByUserIdNoRecordFound() {

        PersonalAttributes existingPersonalAttributes = personalAttributesRepository.findPersonalAttributesByUserId(9999L);

        Assertions.assertThat(existingPersonalAttributes).isNull();
    }
}
