package com.aes.corebackend.service;

//import com.aes.corebackend.dto.ResponseDTO;
import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.UserCredential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.aes.corebackend.dto.APIResponse.getApiResposne;
import static com.aes.corebackend.util.response.APIResponseDesc.*;

public class UserCredentialServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserCredentialServiceTest.class);

    @InjectMocks
    private UserCredentialService userCredentialService;

    private UserCredential userCredentialTest = new UserCredential(1L, "012580", "123@5Aa", true, "EMPLOYEE");

    private APIResponse expectedResponse = getApiResposne();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userCredentialService = Mockito.mock(UserCredentialService.class);
    }

    @Test
    public void saveTest() throws Exception {

        expectedResponse.setResponse(USER_CREDENTIAL_CREATED_SUCCESSFULLY, TRUE, userCredentialTest);
        Mockito.when(userCredentialService.save(userCredentialTest)).thenReturn(expectedResponse);
        APIResponse actualResponse = userCredentialService.save(userCredentialTest);
        assert actualResponse.getMessage().equals(expectedResponse.getMessage());

        expectedResponse.setResponse(USER_CREDENTIAL_CREATION_FAILED, FALSE, null);
        Mockito.when(userCredentialService.save(userCredentialTest)).thenReturn(expectedResponse);
        actualResponse = userCredentialService.save(userCredentialTest);
        assert actualResponse.getMessage().equals(expectedResponse.getMessage());
    }

    @Test
    public void updateTest_success() {
        UserCredential updatedUserCredential = new UserCredential();
        updatedUserCredential.setEmployeeId("012580");
        updatedUserCredential.setPassword("123$5Aa");

        userCredentialTest.setPassword("123$5Aa");
        expectedResponse.setResponse(USER_CREDENTIAL_UPDATED_SUCCESSFULLY, TRUE, userCredentialTest);
        Mockito.when(userCredentialService.update(updatedUserCredential)).thenReturn(expectedResponse);

        APIResponse actualResponse = userCredentialService.update(updatedUserCredential);
        System.out.println(actualResponse.toString());
        assert actualResponse.getMessage().equals(expectedResponse.getMessage());


        updatedUserCredential.setEmployeeId("013580");
        expectedResponse.setResponse(USER_CREDENTIAL_UPDATE_FAILED, FALSE, null);
        Mockito.when(userCredentialService.update(updatedUserCredential)).thenReturn(expectedResponse);

        actualResponse = userCredentialService.update(updatedUserCredential);
        System.out.println(actualResponse.toString());
        assert actualResponse.getMessage().equals(expectedResponse.getMessage());
    }

    @Test
    public void getEmployeeIdTest() throws Exception {

        Mockito.when(userCredentialService.getEmployeeId(userCredentialTest.getEmployeeId())).thenReturn(userCredentialTest);
        UserCredential userCredential = userCredentialService.getEmployeeId(userCredentialTest.getEmployeeId());

        assert userCredential.getEmployeeId().equals(userCredentialTest.getEmployeeId());
    }

    @Test
    public void verifyCredentialTest() throws Exception {
        UserCredentialDTO validUserCredentialDTO = new UserCredentialDTO();
        validUserCredentialDTO.setEmployeeId("012580");
        validUserCredentialDTO.setPassword("123@5Aa");

        expectedResponse.setResponse(VALID_PASSWORD, TRUE, userCredentialTest);
        Mockito.when(userCredentialService.verifyPassword(validUserCredentialDTO)).thenReturn(expectedResponse);

        APIResponse actualResponse = userCredentialService.verifyPassword(validUserCredentialDTO);
        assert actualResponse.getMessage().equals(expectedResponse.getMessage());


        UserCredentialDTO invalidUserCredentialDTO = new UserCredentialDTO();
        invalidUserCredentialDTO.setEmployeeId("012580");
        invalidUserCredentialDTO.setPassword("123&5Aa");

        expectedResponse.setResponse(INVALID_PASSWORD, FALSE, null);
        Mockito.when(userCredentialService.verifyPassword(invalidUserCredentialDTO)).thenReturn(expectedResponse);

        actualResponse = userCredentialService.verifyPassword(invalidUserCredentialDTO);
        assert actualResponse.getMessage().equals(expectedResponse.getMessage());
    }

    @Test
    public void generateAndSendTempPassTest() throws Exception {
        String email = "abc@gmail.com";

        expectedResponse.setResponse(NEW_PASSWORD_SENT, TRUE, userCredentialTest);
        Mockito.when(userCredentialService.generateAndSendTempPass(email)).thenReturn(expectedResponse);

        APIResponse actualResponse = userCredentialService.generateAndSendTempPass(email);
        assert actualResponse.getMessage().equals(expectedResponse.getMessage());

        expectedResponse.setResponse(EMPLOYEE_NOT_FOUND, FALSE, null);
        Mockito.when(userCredentialService.generateAndSendTempPass(email)).thenReturn(expectedResponse);

        actualResponse = userCredentialService.generateAndSendTempPass(email);
        assert actualResponse.getMessage().equals(expectedResponse.getMessage());
    }
}
