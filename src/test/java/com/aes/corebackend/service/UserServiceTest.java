package com.aes.corebackend.service;

import com.aes.corebackend.controller.UserController;
import com.aes.corebackend.dto.ResponseDTO;
import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    private UserCredentialService userCredentialService;

    ObjectMapper om = new ObjectMapper();
    UserCredential userCredential_1 = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_2 = new UserCredential(2,"102","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_3 = new UserCredential(3,"103","a1wq",true,"EMPLOYEE");
    User user_1 = new User(1L,"abc@gmail.com","agm","101","a1polymar","accounts","EMPLOYEE",userCredential_1);
    User user_2 = new User(2L,"abd@gmail.com","agm","102","a1polymar","accounts","EMPLOYEE",userCredential_2);
    User user_3 = new User(3L,"abe@gmail.com","agm","103","a1polymar","accounts","EMPLOYEE",userCredential_3);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userCredentialService = Mockito.mock(UserCredentialService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userService = Mockito.mock(UserService.class);
    }
    @Test
    public void createUserTest() throws Exception {
        UserCredential userCredential = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
        User user = new User();
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");
        user.setRoles("EMPLOYEE");
        UserDTO userDto = new UserDTO();
        userDto.setDesignation("agm");
        userDto.setDepartment("accounts");
        userDto.setEmailAddress("mdahad118@gmail.com");
        userDto.setBusinessUnit("a1polymar");
        userDto.setEmployeeId("0101");
        userDto.setRoles("EMPLOYEE");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("user created successfully");
        responseDTO.setSuccess(true);
        responseDTO.setData(null);
        user.setUserCredential(userCredential);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userService.create(user,userDto)).thenReturn(responseDTO);
        assertEquals(userService.create(user,userDto).getMessage(),responseDTO.getMessage());
        assertEquals(userService.create(user,userDto).getData(),responseDTO.getData());
    }
    @Test
    public void getAllUsers_success() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1,user_2,user_3));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userService.read()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8081/users").header(HttpHeaders.AUTHORIZATION,"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMTI1MTkiLCJleHAiOjE2NDM4MTk4ODAsImlhdCI6MTY0Mzc4Mzg4MH0.5LF-tn-BGh20YpushocQv9pNLPaI1P_MDsxriO6w3zc")
                        .contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk());
    }
    @Test
    public void getUserDetailsTest() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user_1));
        Mockito.when(userService.read(1L)).thenReturn(user_1);
    }
    @Test
    public void updateUserById() throws Exception {
        User user_1_temp = new User(1L,"abc@gmail.com","dgm","0101","a1polymar","accounts","EMPLOYEE",userCredential_1);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("user updated successfully");
        responseDTO.setSuccess(true);
        responseDTO.setData(null);
        Mockito.when(userService.update(user_1_temp,1L)).thenReturn(responseDTO);
        assertEquals(userService.update(user_1_temp,1L).getMessage(),responseDTO.getMessage());
        assertEquals(userService.update(user_1_temp,1L).getData(),responseDTO.getData());
    }
}