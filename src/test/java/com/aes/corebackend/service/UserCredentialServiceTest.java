package com.aes.corebackend.service;

import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserCredentialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserCredentialServiceTest {

    @Autowired
    private UserCredentialService userCredentialService;

    @MockBean
    private UserCredentialRepository userCredentialRepository;

    @Test
    @DisplayName("Test getEmployee by employee id Success")
    public void testGetEmployee() {
        UserCredential mockUserCredential = new UserCredential(1, "012580", "123@5Aa", true, "EMPLOYEE");
        Mockito.when(userCredentialRepository.findByEmployeeId(mockUserCredential.getEmployeeId())).thenReturn(Optional.of(mockUserCredential));

        UserCredential returnedUserCredentialFromService = userCredentialService.getEmployee("012580");
        assertEquals(returnedUserCredentialFromService,mockUserCredential);
    }

    public void testSave() {

    }

}
