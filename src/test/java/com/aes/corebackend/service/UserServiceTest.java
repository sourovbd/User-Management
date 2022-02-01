package com.aes.corebackend.service;

import com.aes.corebackend.controller.UserController;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private UserCredentialService userCredentialService;

    ObjectMapper om = new ObjectMapper();
    UserCredential userCredential_1 = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_2 = new UserCredential(2,"102","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_3 = new UserCredential(3,"103","a1wq",true,"EMPLOYEE");
    User user_1 = new User(1L,"abc@gmail.com","agm","101","a1polymar","accounts",userCredential_1);
    User user_2 = new User(2L,"abd@gmail.com","agm","102","a1polymar","accounts",userCredential_2);
    User user_3 = new User(3L,"abe@gmail.com","agm","103","a1polymar","accounts",userCredential_3);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userCredentialService = Mockito.mock(UserCredentialService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
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
        user.setUserCredential(userCredential);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userService.create(user)).thenReturn(user);
        // UserCreationResponseDTO response = om.rea  dValue(resultContent, UserCreationResponseDTO.class);
        // assertEquals(response.getMessage(),"user created");
    }
    @Test
    public void getAllUsers_success() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1,user_2,user_3));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userService.read()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON )).andExpect(status().isOk());
    }
    @Test
    public void getUserByDetailsTest() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user_1));
        Mockito.when(userService.read(1L)).thenReturn(user_1);
    }
    @Test
    public void updateUserById() throws Exception {
        User user_1_temp = new User(1,"abc@gmail.com","dgm","101","a1polymar","accounts",userCredential_1);
        // Mockito.when(userService.update(user_1_temp,1L)).thenReturn(true);
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