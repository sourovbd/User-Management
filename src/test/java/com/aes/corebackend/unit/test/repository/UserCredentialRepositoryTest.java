package com.aes.corebackend.unit.test.repository;

import com.aes.corebackend.entity.usermanagement.UserCredential;
import com.aes.corebackend.repository.usermanagement.UserCredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserCredentialRepositoryTest {

    @Mock
    private UserCredentialRepository userCredentialRepository;

    private UserCredential userCredentialTest = new UserCredential(1L, "012580", "123@5Aa", true, "EMPLOYEE");

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveTest() throws Exception {
        Mockito.when(userCredentialRepository.save(userCredentialTest)).thenReturn(userCredentialTest);
        assert userCredentialRepository.save(userCredentialTest).getId() == 1;
    }

    @Test
    public void findByIdTest() throws Exception {
        Optional<UserCredential> userCredential = Optional.of(userCredentialTest);
        Mockito.when(userCredentialRepository.findById(1L)).thenReturn(userCredential);

        Optional<UserCredential> userCredentialRet = userCredentialRepository.findById(1L);
        assert userCredentialRet.get().getEmployeeId().equals(userCredentialTest.getEmployeeId());
    }

    @Test
    public void findByEmployeeIdTest() throws Exception {
        Optional<UserCredential> userCredential = Optional.of(userCredentialTest);
        Mockito.when(userCredentialRepository.findByEmployeeId("012580")).thenReturn(userCredential);

        Optional<UserCredential> resUserCredential = userCredentialRepository.findByEmployeeId("012580");
        assert resUserCredential.get().getEmployeeId().equals(userCredentialTest.getEmployeeId());
    }
}
