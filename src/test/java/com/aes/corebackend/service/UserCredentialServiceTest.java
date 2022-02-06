package com.aes.corebackend.service;

import com.aes.corebackend.dto.ResponseDTO;
import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.UserCredential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCredentialServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserCredentialServiceTest.class);

    @InjectMocks
    private UserCredentialService userCredentialService;

    private UserCredential userCredentialTest = new UserCredential(1L, "012580", "123@5Aa", true, "EMPLOYEE");

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userCredentialService = Mockito.mock(UserCredentialService.class);
    }

    @Test
    public void saveTest() throws Exception {
        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setResponses("Saved Successfully", true, userCredentialTest);

        Mockito.when(userCredentialService.save(userCredentialTest)).thenReturn(expectedResponse);
        ResponseDTO actualResponse = userCredentialService.save(userCredentialTest);

        assert actualResponse.getMessage().equals(expectedResponse.getMessage());
        assert actualResponse.getData() == expectedResponse.getData();
    }

    @Test
    public void updateTest() {
        UserCredential userCredential = new UserCredential(1L, "012580", "123$5Aa", true, "EMPLOYEE");

        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setResponses("Success", true, userCredential);

        Mockito.when(userCredentialService.update(userCredentialTest)).thenReturn(expectedResponse);
        ResponseDTO actualResponse = userCredentialService.update(userCredentialTest);

        assert actualResponse.getMessage().equals(expectedResponse.getMessage());
    }

    @Test
    public void verifyCredentialTest() throws Exception {
        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setEmployeeId("012580");
        userCredentialDTO.setPassword("123@5Aa");

        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setResponses("Valid Password", true, userCredentialTest);

        Mockito.when(userCredentialService.verifyPassword(userCredentialDTO)).thenReturn(expectedResponse);
        ResponseDTO actualResponse = userCredentialService.verifyPassword(userCredentialDTO);

        assert actualResponse.getMessage().equals(expectedResponse.getMessage());
    }

    @Test
    public void generateAndSendTempPassTest() throws Exception {
        String email = "abc@gmail.com";

        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setResponses("A new password is sent to your email.", true, userCredentialTest);

        Mockito.when(userCredentialService.generateAndSendTempPass(email)).thenReturn(expectedResponse);
        ResponseDTO actualResponse = userCredentialService.generateAndSendTempPass(email);

        assert actualResponse.getMessage().equals(expectedResponse.getMessage());
    }
}
