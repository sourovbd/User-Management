package com.aes.corebackend.controller;

import com.aes.corebackend.dto.ForgotPasswordDTO;
import com.aes.corebackend.dto.ResponseDTO;
import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.service.UserCredentialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserCredentialControllerTestTemp {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserCredentialService userCredentialService;

    @InjectMocks
    private UserCredentialController userCredentialController;

    ObjectMapper om = new ObjectMapper();

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts","EMPLOYEE", null);
    private UserCredential userCredentialTest = new UserCredential(1L, "012580", "123@5Aa7", true, "EMPLOYEE");

    private ResponseDTO expectedResponse = new ResponseDTO();

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

        expectedResponse.setResponses("Password must be 8 or more characters in length.", false, null);
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("123456Aa");
        expectedResponse.setMessage("Password must contain 1 or more special characters.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("123456@a");
        expectedResponse.setMessage("Password must contain 1 or more uppercase characters.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("123456@A");
        expectedResponse.setMessage("Password must contain 1 or more lowercase characters.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("bbbccc@Aa");
        expectedResponse.setMessage("Password must contain 1 or more digit characters.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("bbb 1cc@Aa");
        expectedResponse.setMessage("Password contains a whitespace character.");
        badRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword(userCredentialTest.getPassword());
        expectedResponse.setResponses("Saved Successfully", true, userCredentialTest);
        Mockito.when(userCredentialService.save(userCredentialTest)).thenReturn(expectedResponse);
        successRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword(userCredentialTest.getPassword());
        expectedResponse.setResponses("Save Failed", false, null);
        Mockito.when(userCredentialService.save(userCredentialTest)).thenReturn(expectedResponse);
        successRequestHandler("/users-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);
    }

    @Test
    public void updateCredentialTest() throws Exception {

        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setEmployeeId("012580");
        userCredentialDTO.setPassword("12345@Aa");

        userCredentialTest.setPassword(userCredentialDTO.getPassword());
        expectedResponse.setResponses("Success", true, userCredentialTest);
        Mockito.when(userCredentialService.update(userCredentialDTO.to(userCredentialDTO))).thenReturn(expectedResponse);
        successRequestHandler("/users/reset-password", om.writeValueAsString(userCredentialDTO), expectedResponse);

        expectedResponse.setResponses("Failed", false, null);
        Mockito.when(userCredentialService.update(userCredentialDTO.to(userCredentialDTO))).thenReturn(expectedResponse);
        successRequestHandler("/users/reset-password", om.writeValueAsString(userCredentialDTO), expectedResponse);
    }

    @Test
    public void verifyCredentialTest() throws Exception {
        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setEmployeeId("012580");
        userCredentialDTO.setPassword("123@5Aa7");

        user.setUserCredential(userCredentialTest);
        expectedResponse.setResponses("Valid Password", true, user);
        Mockito.when(userCredentialService.verifyPassword(userCredentialDTO)).thenReturn(expectedResponse);
        successRequestHandler("/users/verify-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);

        userCredentialDTO.setPassword("123%5Aa7");
        expectedResponse.setResponses("Invalid Password", false, null);
        successRequestHandler("/users/verify-credential", om.writeValueAsString(userCredentialDTO), expectedResponse);
    }

    @Test
    public void forgotPasswordTest() throws Exception {
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();

        forgotPasswordDTO.setEmailAddress("ab c@yahoo.com");
        expectedResponse.setResponses("email id is invalid", false, null);
        Mockito.when(userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress())).thenReturn(expectedResponse);
        badRequestHandler("/users/forgot-password", om.writeValueAsString(forgotPasswordDTO), expectedResponse);

        forgotPasswordDTO.setEmailAddress("abc@yahoo.com");
        expectedResponse.setResponses("A new password is sent to your email.", true, userCredentialTest);
        Mockito.when(userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress())).thenReturn(expectedResponse);
        successRequestHandler("/users/forgot-password", om.writeValueAsString(forgotPasswordDTO), expectedResponse);
    }

    private void successRequestHandler(String uri, String requestJsonContent, ResponseDTO expectedResponse) throws Exception {

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

    private void badRequestHandler(String uri, String requestJsonContent, ResponseDTO expectedResponse) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }
}
