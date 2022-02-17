package com.aes.corebackend.integrationtest.controller;

import com.aes.corebackend.dto.usermanagement.UserDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.usermanagement.UserCredential;
import com.aes.corebackend.repository.usermanagement.UserCredentialRepository;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.aes.corebackend.service.springsecurity.CustomUserDetailsService;

import com.aes.corebackend.service.usermanagement.UserService;
import com.aes.corebackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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

import java.util.List;

import static com.aes.corebackend.util.response.UMAPIResponseMessage.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
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
    @DisplayName("GET /users - Fetch All Existing Users Success")
    @DatabaseSetup("/dataset/users.xml")
    public void getAllUsersTest() throws Exception {
        List<User> users = userRepository.findAll();
        System.out.println("size: "+users.size());
        List<UserCredential> userCredentialList = userCredentialRepository.findAll();
        System.out.println("size: "+userCredentialList.size());

        mockMvc.perform(get("/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(USER_FETCH_OK));
    }

    @Test
    @DisplayName("POST /users - Create New User Success")
    @DatabaseSetup("/dataset/users.xml")
    public void createUserTest() throws Exception {
        UserDTO userDto =  UserDTO
                .builder()
                .businessUnit("a1polymer")
                .department("accounts")
                .designation("agm")
                .emailAddress("xyz@gmail.com")
                .employeeId("101")
                .roles("EMPLOYEE,SYS_ADMIN")
                .build();

        String jsonRequest = om.writeValueAsString(userDto);
        mockMvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(USER_CREATED_SUCCESSFULLY));
    }

    @Test
    @DisplayName("GET /users - Create New User Success")
    @DatabaseSetup("/dataset/users.xml")
    public void getUserDetailsTest() throws Exception {

        mockMvc.perform(get("/users/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("user found"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.emailAddress").value("test2@gmail.com"))
                .andExpect(jsonPath("$.data.designation").value("CTO"))
                .andExpect(jsonPath("$.data.employeeId").value("012518"))
                .andExpect(jsonPath("$.data.businessUnit").value("AES"))
                .andExpect(jsonPath("$.data.department").value("Developmemt"))
                .andExpect(jsonPath("$.data.roles").value("EMPLOYEE"))
                .andExpect(jsonPath("$.data.userCredential.active").value(true))
                .andExpect(jsonPath("$.data.userCredential.password").value("$2a$12$XMG3cM8IzAtwe3NsWn/HRuWmvUJ3YNzDMOCEysSoonoDihx6Dsysi"));
    }

    @Test
    @DisplayName("PUT /users/{id} - Update Existing User Success")
    @DatabaseSetup("/dataset/users.xml")
    public void updateUserById() throws Exception {
        UserDTO userDto =  UserDTO
                .builder()
                .businessUnit("a1polymer")
                .department("ACCOUNTS")
                .designation("AGM")
                .emailAddress("xyz@gmail.com")
                .employeeId("012518")
                .roles("EMPLOYEE,SYS_ADMIN")
                .build();

        String jsonRequest = om.writeValueAsString(userDto);
        mockMvc.perform(put("/users/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(USER_UPDATED_SUCCESSFULLY))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.emailAddress").value("xyz@gmail.com"))
                .andExpect(jsonPath("$.data.designation").value("AGM"))
                .andExpect(jsonPath("$.data.employeeId").value("012518"))
                .andExpect(jsonPath("$.data.businessUnit").value("a1polymer"))
                .andExpect(jsonPath("$.data.department").value("ACCOUNTS"))
                .andExpect(jsonPath("$.data.roles").value("EMPLOYEE,SYS_ADMIN"))
                .andExpect(jsonPath("$.data.userCredential.active").value(true));
    }
}