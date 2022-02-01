package com.aes.corebackend.service;

import com.aes.corebackend.controller.UserController;
import com.aes.corebackend.dto.UserCreationResponseDTO;
import com.aes.corebackend.entity.User;
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

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
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