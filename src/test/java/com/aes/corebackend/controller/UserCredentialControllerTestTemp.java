package com.aes.corebackend.controller;

import com.aes.corebackend.dto.ForgotPasswordDTO;
import com.aes.corebackend.dto.ResponseDTO;
import com.aes.corebackend.dto.UserCredentialDTO;
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
    private UserCredential userCredentialTest = new UserCredential(1L, "012580", "123@5Aa7", true, "EMPLOYEE");

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userCredentialController)
                .build();
    }

    @Test
    public void saveCredentialTest() throws Exception {

        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setResponses("Saved Successfully", true, userCredentialTest);

        Mockito.when(userCredentialService.save(userCredentialTest)).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users-credential/012580")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userCredentialTest));

        String actualResponse = mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assert om.writeValueAsString(expectedResponse).equals(actualResponse);
    }

    @Test
    public void updateCredentialTest() throws Exception {

        UserCredential userCredential = new UserCredential(1L, "012580", "1423$5Aa", true, "EMPLOYEE");

        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setResponses("Updated Successfully.", true, userCredential);

        Mockito.when(userCredentialService.update(userCredential, "012580")).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/012580/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userCredential));

        String actualResponse = mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assert om.writeValueAsString(expectedResponse).equals(actualResponse);
    }

    @Test
    public void verifyCredentialTest() throws Exception {
        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setEmployeeId("012580");
        userCredentialDTO.setPassword("123@5Aa");

        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setResponses("Valid Password", true, userCredentialTest);

        Mockito.when(userCredentialService.verifyPassword(userCredentialDTO)).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/verify-credential")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userCredentialDTO));

        String actualResponse = mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assert om.writeValueAsString(expectedResponse).equals(actualResponse);
    }

    @Test
    public void forgotPasswordTest() throws Exception {
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmailAddress("abc@yahoo.com");

        ResponseDTO responseDTO =  new ResponseDTO();
        responseDTO.setResponses("A new password is sent to your email.", true, null);
        Mockito.when(userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress())).thenReturn(responseDTO);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(forgotPasswordDTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("A new password is sent to your email."))
                .andExpect(jsonPath("$.success").value("true"));
    }
}
