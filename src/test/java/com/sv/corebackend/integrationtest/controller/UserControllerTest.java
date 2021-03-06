package com.sv.corebackend.integrationtest.controller;

import com.sv.corebackend.dto.usermanagement.UserDTO;
import com.sv.corebackend.repository.UserCredentialRepository;
import com.sv.corebackend.repository.UserRepository;
import com.sv.corebackend.service.springsecurity.CustomUserDetailsService;

import com.sv.corebackend.service.usermanagement.UserService;
import com.sv.corebackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sv.corebackend.util.response.UMAPIResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
public class UserControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    private static String USERNAME = "012615";
    private static String TOKEN = "";
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        System.out.println("username:  "+userDetails.getUsername());
        TOKEN = jwtTokenUtil.generateToken(userDetails);
    }
    UserDTO createUserDto =  UserDTO.builder()
            .businessUnit("a1polymer")
            .department("accounts")
            .designation("agm")
            .emailAddress("xyz@gmail.com")
            .employeeId("012610")
            .roles("EMPLOYEE,SYS_ADMIN")
            .build();

    UserDTO updateUserDto =  UserDTO
            .builder()
            .businessUnit("a1polymer")
            .department("ACCOUNTS")
            .designation("AGM")
            .emailAddress("test@gmail.com")
            .employeeId("012615")
            .roles("EMPLOYEE,SYS_ADMIN")
            .build();
    @Test
    @DisplayName("GET /users - Fetch All Existing Users Success")
    @DatabaseSetup("/dataset/data.xml")
    public void getAllUsersTest() throws Exception {

        mockMvc.perform(get("/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.USER_FETCH_OK));
    }

    @Test
    @DisplayName("POST /users - Create New User Success")
    @DatabaseSetup("/dataset/data.xml")
    public void createUserTestSucceed() throws Exception {

        String jsonRequest = om.writeValueAsString(createUserDto);
        mockMvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.USER_CREATED_SUCCESSFULLY))
                .andExpect(jsonPath("$.data.emailAddress").value("xyz@gmail.com"))
                .andExpect(jsonPath("$.data.employeeId").value("012610"));
    }

    @Test
    @DisplayName("GET /users - Fetch Single User Success")
    @DatabaseSetup("/dataset/data.xml")
    public void getUserDetailsTestSucceed() throws Exception {

        mockMvc.perform(get("/users/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.USER_FOUND))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.emailAddress").value("test@gmail.com"))
                .andExpect(jsonPath("$.data.designation").value("CTO"))
                .andExpect(jsonPath("$.data.employeeId").value("012517"))
                .andExpect(jsonPath("$.data.businessUnit").value("AES"))
                .andExpect(jsonPath("$.data.department").value("Developmemt"))
                .andExpect(jsonPath("$.data.roles").value("EMPLOYEE"))
                .andExpect(jsonPath("$.data.userCredential.active").value(true))
                .andExpect(jsonPath("$.data.userCredential.password").value("$2a$12$XMG3cM8IzAtwe3NsWn/HRuWmvUJ3YNzDMOCEysSoonoDihx6Dsysi"));
    }

    @Test
    @DisplayName("GET /users - Fetch Single User Fail")
    @DatabaseSetup("/dataset/data.xml")
    public void getUserDetailsTestFail() throws Exception {

        mockMvc.perform(get("/users/99")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.USER_NOT_FOUND));
    }

    @Test
    @DisplayName("PUT /users/{id} - Update Existing User Success")
    @DatabaseSetup("/dataset/data.xml")
    public void updateUserByIdSucceed() throws Exception {

        String jsonRequest = om.writeValueAsString(updateUserDto);
        mockMvc.perform(put("/users/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.USER_UPDATED_SUCCESSFULLY))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.emailAddress").value("test@gmail.com"))
                .andExpect(jsonPath("$.data.designation").value("AGM"))
                .andExpect(jsonPath("$.data.employeeId").value("012615"))
                .andExpect(jsonPath("$.data.businessUnit").value("a1polymer"))
                .andExpect(jsonPath("$.data.department").value("ACCOUNTS"))
                .andExpect(jsonPath("$.data.roles").value("EMPLOYEE,SYS_ADMIN"))
                .andExpect(jsonPath("$.data.userCredential.active").value(true));
    }

    @Test
    @DisplayName("PUT /users/{id} - Update Existing User Success")
    @DatabaseSetup("/dataset/data.xml")
    public void updateUserByIdFailed() throws Exception {

        String jsonRequest = om.writeValueAsString(updateUserDto);
        mockMvc.perform(put("/users/99")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(UMAPIResponseMessage.USER_UPDATE_FAILED));;
    }
}