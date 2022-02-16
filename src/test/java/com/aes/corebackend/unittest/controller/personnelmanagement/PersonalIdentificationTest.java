package com.aes.corebackend.unittest.controller.personnelmanagement;

import com.aes.corebackend.controller.personnelmanagement.PersonalIdentificationController;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalIdentificationInfoDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
import com.aes.corebackend.service.personnelmanagement.PersonalIdentificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonalIdentificationTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonalIdentificationController controller;

    @Mock
    private PersonalIdentificationService service;

    ObjectMapper om = new ObjectMapper();
    User user = new User();
    PersonalIdentificationInfoDTO idDTO = new PersonalIdentificationInfoDTO();

    @BeforeEach
    public void setup() {   //Initialize mocks with openMocks, creating a mock controller
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        user.setId(1L);
        user.setDesignation("MD");
        user.setDepartment("Company");
        user.setEmailAddress("hossainfurkaan@gmail.com");
        user.setBusinessUnit("Anwar Enterprise Systems Ltd.");
        user.setEmployeeId("0001");

        idDTO.setEtin("010101010101");
        idDTO.setNationalID("012345678910");
    }

    @Test
    public void createIdentificationTest() throws Exception {

        APIResponse expectedResponse = new APIResponse();
        expectedResponse.setResponse(IDENTIFICATION_CREATE_SUCCESS, TRUE, null, SUCCESS); // This is the expected value as well wierdly

        Mockito.when(service.create(idDTO, user.getId())).thenReturn(expectedResponse); // initialize service with expected response

        String jsonRequest = om.writeValueAsString(idDTO);  // payload
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/1/identification-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(IDENTIFICATION_CREATE_SUCCESS))
                .andExpect(jsonPath("$.success").value(TRUE));
    }

    @Test
    public void updateAIdentificationTest() throws Exception {

        APIResponse expectedResponse = new APIResponse(IDENTIFICATION_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(service.update(idDTO, user.getId())).thenReturn(expectedResponse);// initialize service with expected response

        String jsonRequest = om.writeValueAsString(idDTO);  // payload
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/identification-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(IDENTIFICATION_UPDATE_SUCCESS))
                .andExpect(jsonPath("$.success").value(TRUE));
    }


    @Test
    public void getIdentificationTest() throws Exception {
        PersonalIdentificationInfo id = PersonalIdentificationInfoDTO.getPersonalIdentificationEntity(idDTO);
        APIResponse expectedResponse = new APIResponse(IDENTIFICATION_RECORD_FOUND, TRUE, id, SUCCESS);//Expected return

        Mockito.when(service.read(user.getId())).thenReturn(expectedResponse);// testing service for read: Controller to Service
        // [Checking if service is available]

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders  //client to controller-- > just the request object
                .get("/users/1/identification-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)    //Call controller using request object
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(IDENTIFICATION_RECORD_FOUND))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(id));
    }
}
