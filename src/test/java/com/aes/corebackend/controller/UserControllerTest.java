package com.aes.corebackend.controller;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    ObjectMapper om = new ObjectMapper();
    UserCredential userCredential_1 = new UserCredential(1, "101", "a1wq", true, "EMPLOYEE");
    UserCredential userCredential_2 = new UserCredential(2, "102", "a1wq", true, "EMPLOYEE");
    UserCredential userCredential_3 = new UserCredential(3, "103", "a1wq", true, "EMPLOYEE");
    User user_1 = new User(1L, "abc@gmail.com", "agm", "101", "a1polymar", "accounts", "EMPLOYEE", userCredential_1);
    User user_2 = new User(2L, "abd@gmail.com", "agm", "102", "a1polymar", "accounts", "EMPLOYEE", userCredential_2);
    User user_3 = new User(3L, "abe@gmail.com", "agm", "103", "a1polymar", "accounts", "EMPLOYEE", userCredential_3);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void createUserTest() throws Exception {
        UserCredential userCredential = new UserCredential(1, "101", "a1wq", true, "EMPLOYEE");
        User user = new User();
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");
        user.setRoles("EMPLOYEE");
        user.setUserCredential(userCredential);
        String jsonRequest = om.writeValueAsString(user);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsers_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .header(HttpHeaders.AUTHORIZATION, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMTI1MTkiLCJleHAiOjE2NDM4MTk4ODAsImlhdCI6MTY0Mzc4Mzg4MH0.5LF-tn-BGh20YpushocQv9pNLPaI1P_MDsxriO6w3zc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserDetailsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/11")
                        //.header(HttpHeaders.AUTHORIZATION, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMTI1MTkiLCJleHAiOjE2NDM4MTk4ODAsImlhdCI6MTY0Mzc4Mzg4MH0.5LF-tn-BGh20YpushocQv9pNLPaI1P_MDsxriO6w3zc")
                       // .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserById() throws Exception {
        UserCredential userCredential = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
        User user = new User();
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");
        user.setRoles("EMPLOYEE");
        user.setUserCredential(userCredential);
        String jsonRequest = om.writeValueAsString(user);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);
        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }

}
