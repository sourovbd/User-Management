package com.aes.corebackend.service;

import com.aes.corebackend.controller.UserController;
import com.aes.corebackend.dto.UserCreationResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private UserController userController;
    @InjectMocks
    private UserCredential userCredential;

    ObjectMapper om = new ObjectMapper();
    UserCredential userCredential_1 = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_2 = new UserCredential(2,"102","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_3 = new UserCredential(3,"103","a1wq",true,"EMPLOYEE");
    User user_1 = new User(1,"abc@gmail.com","agm","101","a1polymar","accounts",userCredential_1);
    User user_2 = new User(2,"abd@gmail.com","agm","102","a1polymar","accounts",userCredential_2);
    User user_3 = new User(3,"abe@gmail.com","agm","103","a1polymar","accounts",userCredential_3);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = Mockito.mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @Test
    public void createUserTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        String jsonRequest = om.writeValueAsString(user);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user/create").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(jsonRequest);
        mockMvc.perform(mockRequest).andExpect(status().isOk());
        // UserCreationResponseDTO response = om.readValue(resultContent, UserCreationResponseDTO.class);
        // assertEquals(response.getMessage(),"user created");
    }
    @Test
    public void getAllUsers_success() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1,user_2,user_3));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/get/users").contentType(MediaType.APPLICATION_JSON )).andExpect(status().isOk());
    }
    /*@Autowired
    private UserService service ;
    @MockBean
    private UserRepository repository;

    @Test
    public void saveUserTest() {
        User user = new User(1, "ahad.alam@anwargroup.net", "agm", "0101", "a1polymar", "accounts");
        when(repository.save(user)).thenReturn(user);
        assertEquals(user,service.save(user));
    }

    @Test
    void update() {
    }

    @Test
    public void findByIdTest() {
        long id = 1;
        User userMock = new User(1, "ahad.alam@anwargroup.net", "agm", "0101", "a1polymar", "accounts");
        when(repository.findById(id)).thenReturn(Optional.of(userMock));
        //User userService = service.findById(id);

    }*/
}