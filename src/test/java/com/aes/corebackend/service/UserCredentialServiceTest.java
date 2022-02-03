package com.aes.corebackend.service;

import com.aes.corebackend.controller.UserController;
import com.aes.corebackend.controller.UserCredentialController;
import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserCredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//@RunWith(MockitoJUnitRunner.class)
public class UserCredentialServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @InjectMocks
    private UserCredentialService userCredentialService;

    @InjectMocks
    private UserCredentialController userCredentialController;

    private static final boolean ACTUAL = false;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userCredentialService = Mockito.mock(UserCredentialService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userCredentialController)
                .build();
    }

    @Test
    public void saveTest() throws Exception {
        UserCredential userCredential = new UserCredential(27, "12471", "123456", true, "EMPLOYEE");

        Mockito.when(userCredentialRepository.save(userCredential)).thenReturn(userCredential);
        //Mockito.when(userCredentialService.save(userCredential)).thenReturn(ACTUAL);
    }

    @Test
    public void verifyCredentialTest() throws Exception {
        String uri = "/users/verify-credential";

        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setEmployeeId("1234");
        userCredentialDTO.setPassword("12@Ua34");

        /*MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)//(MediaType.APPLICATION_JSON)//
                .content(userCredentialDTO.toString())).andReturn();

        System.out.println("---->" + mvcResult.getResponse().getContentAsString());//mvcResult.toString());*/

    }
}
