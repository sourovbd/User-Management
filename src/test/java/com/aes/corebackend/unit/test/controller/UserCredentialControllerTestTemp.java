package com.aes.corebackend.unit.test.controller;

import com.aes.corebackend.controller.usermanagement.UserCredentialController;
import com.aes.corebackend.dto.usermanagement.ForgotPasswordDTO;
import com.aes.corebackend.dto.usermanagement.UserCredentialDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.usermanagement.UserCredential;
import com.aes.corebackend.service.usermanagement.UserCredentialService;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.util.response.APIResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.aes.corebackend.util.response.APIResponse.getApiResponse;
import static com.aes.corebackend.util.response.APIResponseStatus.*;
import static com.aes.corebackend.util.response.UMAPIResponseMessage.NEW_PASSWORD_SENT;
import static com.aes.corebackend.util.response.UMAPIResponseMessage.USER_CREDENTIAL_UPDATED_SUCCESSFULLY;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserCredentialControllerTestTemp {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserCredentialService userCredentialService;

    @InjectMocks
    private UserCredentialController userCredentialController;

    ObjectMapper om = new ObjectMapper();

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts","EMPLOYEE", null, null, null);
    private UserCredential userCredentialTest = new UserCredential(1L, "012580", "123@5Aa7", true, "EMPLOYEE");
    private APIResponse expectedResponse = getApiResponse();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userCredentialController)
                .build();
    }

    @Test
    public void saveCredentialTest() throws Exception {

        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setId(1L);
        userCredentialDTO.setEmployeeId("012580");
        userCredentialDTO.setPassword("1234@Aa");
        userCredentialDTO.setActive(true);
        userCredentialDTO.setRoles("EMPLOYEE");

        expectedResponse.setResponse(null, false, null, ERROR);
        expectedResponse.addFieldError("password","Password must be 8 or more characters in length.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("123456Aa");
        expectedResponse.clear();
        expectedResponse.addFieldError("password","Password must contain 1 or more special characters.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("123456@a");
        expectedResponse.clear();
        expectedResponse.addFieldError("password","Password must contain 1 or more uppercase characters.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("123456@A");
        expectedResponse.clear();
        expectedResponse.addFieldError("password","Password must contain 1 or more lowercase characters.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("bbbccc@Aa");
        expectedResponse.clear();
        expectedResponse.addFieldError("password","Password must contain 1 or more digit characters.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("bbb 1cc@Aa");
        expectedResponse.clear();
        expectedResponse.addFieldError("password","Password contains a whitespace character.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword(userCredentialTest.getPassword());
        expectedResponse.clear();
        expectedResponse.setResponse(USER_CREDENTIAL_UPDATED_SUCCESSFULLY, true, userCredentialTest,SUCCESS);
        Mockito.when(userCredentialService.update(userCredentialTest)).thenReturn(expectedResponse);
        successRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        /*userCredentialDTO.setPassword(userCredentialTest.getPassword());
        expectedResponse.setResponse("Save Failed", false, null, ERROR);
        Mockito.when(userCredentialService.update(userCredentialTest)).thenReturn(expectedResponse);
        successRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);*/
    }

    @Test
    public void updateCredentialTest() throws Exception {

        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setEmployeeId("012580");
        userCredentialDTO.setPassword("12345@Aa");

        userCredentialTest.setPassword(userCredentialDTO.getPassword());
        expectedResponse.setResponse("Success", true, userCredentialTest, SUCCESS);
        Mockito.when(userCredentialService.update(userCredentialDTO.to(userCredentialDTO))).thenReturn(expectedResponse);
        successRequestHandler("/users/reset-password", om.writeValueAsString(userCredentialDTO), expectedResponse);

        /*expectedResponse.setResponse("Failed", false, null, ERROR);
        Mockito.when(userCredentialService.update(userCredentialDTO.to(userCredentialDTO))).thenReturn(expectedResponse);
        successRequestHandler("/users/reset-password", om.writeValueAsString(userCredentialDTO), expectedResponse);*/
    }

    @Test
    public void verifyCredentialTest() throws Exception {
        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setEmployeeId("012580");
        userCredentialDTO.setPassword("123@5Aa7");

        user.setUserCredential(userCredentialTest);
        expectedResponse.setResponse("Valid Password", true, user, SUCCESS);
        Mockito.when(userCredentialService.verifyPassword(userCredentialDTO)).thenReturn(expectedResponse);
        successRequestHandler("/users/verify-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        /*userCredentialDTO.setPassword("123%5Aa7");
        expectedResponse.setResponse("Invalid Password", false, null, VALIDATION_ERROR);
        successRequestHandler("/users/verify-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);*/
    }

    @Test
    public void forgotPasswordTest() throws Exception {
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        expectedResponse.setResponse(null, false, null, ERROR);
        forgotPasswordDTO.setEmailAddress("ab c@yahoo.com");
        //expectedResponse.clear();
        expectedResponse.addFieldError("emailAddress","email id is invalid");
        expectedResponse.setResponse(null, false, null, ERROR);
        Mockito.when(userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress())).thenReturn(expectedResponse);
        badRequestHandler("/users/forgot-password", om.writeValueAsString(forgotPasswordDTO), expectedResponse);

        forgotPasswordDTO.setEmailAddress("abc@yahoo.com");
        expectedResponse.setResponse(NEW_PASSWORD_SENT, true, userCredentialTest, SUCCESS);
        Mockito.when(userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress())).thenReturn(expectedResponse);
        successRequestHandler("/users/forgot-password", om.writeValueAsString(forgotPasswordDTO), expectedResponse);
    }

    private void successRequestHandler(String uri, String requestJsonContent, APIResponse expectedResponse) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    private void badRequestHandler(String uri, String requestJsonContent, APIResponse expectedResponse) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors").value(expectedResponse.getFieldsErrors()));
    }
}
