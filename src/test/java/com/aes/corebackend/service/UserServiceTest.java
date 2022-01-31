package com.aes.corebackend.service;

import com.aes.corebackend.dto.UserCreationResponseDTO;
import com.aes.corebackend.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebAppConfiguration
public class UserServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    ObjectMapper om = new ObjectMapper();
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void createUserTest() throws Exception {
        User user = new User();
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("md.ahadulalam@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");
        String jsonRequest = om.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/users").content(jsonRequest).content(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UserCreationResponseDTO response = om.readValue(resultContent, UserCreationResponseDTO.class);
        assertEquals(response.getMessage(),"user created");
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