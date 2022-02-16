package com.aes.corebackend.unit.test.repository;

import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.exception.ResourceNotFoundException;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static final User user = User.builder()
            .id(3)
            .designation("agm")
            .department("accounts")
            .emailAddress("aajf3@gmail.com")
            .businessUnit("a1polymar")
            .employeeId("012617")
            .roles("EMPLOYEE")
            .build();

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testCreateUserSuccess() {
        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getEmployeeId()).isEqualTo("012617");
        Assertions.assertThat(savedUser.getId()).isEqualTo(3);
        Assertions.assertThat(userRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @Disabled
    @DatabaseSetup("/dataset/users.xml")
    public void testCreateUserFail() {
        user.setEmailAddress("test@gmail.com");
        User savedUser = userRepository.save(user);
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testGetAllUsersSuccess()  {

        List<User> users = userRepository.findAll();

        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testGetAllUsersFail()  {

        List<User> users = userRepository.findAll();

        Assertions.assertThat(users.size()).isNotEqualTo(1);
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testGetUserDetailsSuccess()  {

        User user = userRepository.findById(1l).orElseThrow(ResourceNotFoundException::new);

        Assertions.assertThat(user.getId()).isEqualTo(1);
        Assertions.assertThat(user.getEmployeeId()).isEqualTo("012615");
        Assertions.assertThat(user.getEmailAddress()).isEqualTo("test@gmail.com");
        Assertions.assertThat(user.getId()).isNotEqualTo(3);
        Assertions.assertThat(user.getEmployeeId()).isNotEqualTo("012617");
        Assertions.assertThat(user.getEmailAddress()).isNotEqualTo("test3@gmail.com");
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testGetUserDetailsFail()  {

        Optional<User> user = userRepository.findById(5l);
        Assertions.assertThat(user).isEmpty();
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testUpdateUserSuccess() {

        User existingUser = userRepository.findById(1l).get();
        existingUser.setEmailAddress("tester@gmail.com");
        User updatedUserFromDB = userRepository.save(existingUser);

        Assertions.assertThat(updatedUserFromDB.getId()).isEqualTo(1);
        Assertions.assertThat(updatedUserFromDB.getEmailAddress()).isEqualTo("tester@gmail.com");
        Assertions.assertThat(updatedUserFromDB.getEmployeeId()).isEqualTo("012615");
        Assertions.assertThat(updatedUserFromDB.getEmailAddress()).isNotEqualTo("test@gmail.com");


    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testUpdateUserFail() {

        Optional<User> existingUser = userRepository.findById(10l);

        Assertions.assertThat(existingUser).isEmpty();
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testSingleDeleteUserSuccess() {

        userRepository.deleteById(1l);

        Assertions.assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testSingleDeleteUserFail() {

        userRepository.deleteById(1l);

        Assertions.assertThat(userRepository.findAll().size()).isNotEqualTo(2);
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testFindByEmailAddressSuccess() {

        User user = userRepository.findByEmailAddress("test@gmail.com").orElseThrow(ResourceNotFoundException::new);

        Assertions.assertThat(user.getId()).isEqualTo(1);
        Assertions.assertThat(user.getDesignation()).isEqualTo("CTO");
        Assertions.assertThat(user.getDepartment()).isEqualTo("Developmemt");
        Assertions.assertThat(user.getRoles()).isEqualTo("EMPLOYEE");
        Assertions.assertThat(user.getEmailAddress()).isEqualTo("test@gmail.com");
        Assertions.assertThat(user.getId()).isNotEqualTo(4);
        Assertions.assertThat(user.getDesignation()).isNotEqualTo("agm");
        Assertions.assertThat(user.getDepartment()).isNotEqualTo("accounts");
        Assertions.assertThat(user.getRoles()).isNotEqualTo("SYS_ADMIN");
        Assertions.assertThat(user.getEmailAddress()).isNotEqualTo("testy@gmail.com");
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    public void testFindByEmailAddressFail() {

        Optional<User> user = userRepository.findByEmailAddress("tester@gmail.com");

        Assertions.assertThat(user).isEmpty();
    }

}
