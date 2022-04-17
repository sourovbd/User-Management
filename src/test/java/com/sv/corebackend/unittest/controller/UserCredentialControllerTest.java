package com.sv.corebackend.unittest.controller;

import com.sv.corebackend.controller.usermanagement.UserCredentialController;
import com.sv.corebackend.dto.usermanagement.ForgotPasswordDTO;
import com.sv.corebackend.dto.usermanagement.UserCredentialDTO;
import com.sv.corebackend.entity.User;
import com.sv.corebackend.entity.UserCredential;
import com.sv.corebackend.service.usermanagement.UserCredentialService;
import com.sv.corebackend.util.response.APIResponse;
import com.sv.corebackend.validator.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.corebackend.util.response.APIResponseStatus;
import com.sv.corebackend.util.response.UMAPIResponseMessage;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserCredentialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserCredentialService userCredentialService;

    @InjectMocks
    private UserCredentialController userCredentialController;

    ObjectMapper om = new ObjectMapper();

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts","EMPLOYEE", null);
    private UserCredential userCredentialTest = new UserCredential(1L, "012580", "123@5Aa7", true, "EMPLOYEE");
    private APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userCredentialController)
                .setControllerAdvice(GlobalExceptionHandler.class)
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

        expectedResponse.setResponse(null, false, null, APIResponseStatus.ERROR);
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
        expectedResponse.setResponse(UMAPIResponseMessage.USER_CREDENTIAL_UPDATED_SUCCESSFULLY, true, userCredentialTest, APIResponseStatus.SUCCESS);
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
        expectedResponse.setResponse("Success", true, userCredentialTest, APIResponseStatus.SUCCESS);
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
        expectedResponse.setResponse("Valid Password", true, user, APIResponseStatus.SUCCESS);
        Mockito.when(userCredentialService.verifyPassword(userCredentialDTO)).thenReturn(expectedResponse);
        successRequestHandler("/users/verify-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        /*userCredentialDTO.setPassword("123%5Aa7");
        expectedResponse.setResponse("Invalid Password", false, null, VALIDATION_ERROR);
        successRequestHandler("/users/verify-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);*/
    }

    @Test
    public void forgotPasswordTest() throws Exception {
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        expectedResponse.setResponse(null, false, null, APIResponseStatus.ERROR);
        forgotPasswordDTO.setEmailAddress("ab c@yahoo.com");
        //expectedResponse.clear();
        expectedResponse.addFieldError("emailAddress","email id is invalid");
        expectedResponse.setResponse(null, false, null, APIResponseStatus.ERROR);
        Mockito.when(userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress())).thenReturn(expectedResponse);
        badRequestHandler("/users/forgot-password", om.writeValueAsString(forgotPasswordDTO), expectedResponse);

        forgotPasswordDTO.setEmailAddress("abc@yahoo.com");
        expectedResponse.setResponse(UMAPIResponseMessage.NEW_PASSWORD_SENT, true, userCredentialTest, APIResponseStatus.SUCCESS);
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
