package com.sv.corebackend.integrationtest.controller;

import com.sv.corebackend.dto.usermanagement.ForgotPasswordDTO;
import com.sv.corebackend.dto.usermanagement.UserCredentialDTO;
import com.sv.corebackend.repository.UserCredentialRepository;
import com.sv.corebackend.repository.UserRepository;
import com.sv.corebackend.service.springsecurity.CustomUserDetailsService;
import com.sv.corebackend.service.usermanagement.UserCredentialService;
import com.sv.corebackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sv.corebackend.util.response.UMAPIResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class UserCredentialControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    private static String USERNAME = "012518";
    private static String TOKEN = "";
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        System.out.println("username:  "+userDetails.getUsername());
        TOKEN = jwtTokenUtil.generateToken(userDetails);
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void saveCredentialTest() throws Exception {
        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setId(1L);
        userCredentialDTO.setEmployeeId("012518");
        userCredentialDTO.setPassword("1234@Aa8");
        userCredentialDTO.setActive(true);
        userCredentialDTO.setRoles("EMPLOYEE");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users-credential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCredentialDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.USER_CREDENTIAL_UPDATED_SUCCESSFULLY));
    }

    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void verifyCredentialTest() throws Exception {
        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setId(2L);
        userCredentialDTO.setEmployeeId("012518");
        userCredentialDTO.setPassword("1234");
        userCredentialDTO.setActive(true);
        userCredentialDTO.setRoles("EMPLOYEE");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/verify-credential")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userCredentialDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.VALID_PASSWORD));
    }
    @Test
    @DatabaseSetup("/dataset/data.xml")
    public void forgotPasswordTest() throws Exception {
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmailAddress("test2@gmail.com");
        userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/forgot-password")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(forgotPasswordDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.NEW_PASSWORD_SENT));
    }

}
