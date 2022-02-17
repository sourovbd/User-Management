package com.aes.corebackend.unittest.repository.usermanagement;

import com.aes.corebackend.entity.usermanagement.UserCredential;
import com.aes.corebackend.repository.usermanagement.UserCredentialRepository;
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

import java.util.Optional;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class UserCredentialRepositoryTest {
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    private static final UserCredential userCredential = UserCredential.builder()
            .id(4)
            .employeeId("012616")
            .password("123@5Aa4")
            .active(true)
            .roles("EMPLOYEE")
            .build();

    @Test
    @DatabaseSetup("/dataset/user_credentials_2.xml")
    @DatabaseSetup("/dataset/users_2.xml")
    public void updateTestSuccess() throws Exception {
        UserCredential savedCredential = userCredentialRepository.save(userCredential);
        Assertions.assertThat(savedCredential.getId()).isGreaterThan(0);
        Assertions.assertThat(savedCredential.getEmployeeId()).isEqualTo("012616");
        Assertions.assertThat(savedCredential.isActive()).isEqualTo(true);
        Assertions.assertThat(savedCredential.getRoles()).isEqualTo("EMPLOYEE");
    }

    @Test
    @DatabaseSetup("/dataset/user_credentials_2.xml")
    @DatabaseSetup("/dataset/users_2.xml")
    public void findByIdSuccessTest() throws Exception {
        UserCredential userCredential = userCredentialRepository.findById(2L).orElse(null);
        Assertions.assertThat(userCredential.getId()).isGreaterThan(0);
        Assertions.assertThat(userCredential.getEmployeeId()).isEqualTo("012518");
        Assertions.assertThat(userCredential.getRoles()).isEqualTo("EMPLOYEE");
        Assertions.assertThat(userCredential.isActive()).isEqualTo(true);
    }
    @Test
    @DatabaseSetup("/dataset/user_credentials_2.xml")
    @DatabaseSetup("/dataset/users_2.xml")
    public void findByIdFailTest() throws Exception {
        Optional<UserCredential> userCredential = userCredentialRepository.findById(5L);
        Assertions.assertThat(userCredential).isEmpty();
    }
    @Test
    @DatabaseSetup("/dataset/user_credentials_2.xml")
    @DatabaseSetup("/dataset/users_2.xml")
    public void findByEmployeeIdSuccessTest() throws Exception {
        UserCredential userCredential = userCredentialRepository.findByEmployeeId("012518").orElse(null);
        Assertions.assertThat(userCredential.getId()).isGreaterThan(0);
        Assertions.assertThat(userCredential.getRoles()).isEqualTo("EMPLOYEE");
        Assertions.assertThat(userCredential.isActive()).isEqualTo(true);
    }
    @Test
    @DatabaseSetup("/dataset/user_credentials_2.xml")
    @DatabaseSetup("/dataset/users_2.xml")
    public void findByEmployeeFailTest() throws Exception {
        Optional<UserCredential> userCredential = userCredentialRepository.findByEmployeeId("012616");
        Assertions.assertThat(userCredential).isEmpty();
    }
}