package com.aes.corebackend.controller.personnelmanagement;


import com.aes.corebackend.controller.personnelmanagement.PersonalAttributesController;
import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.service.personnelmanagement.PersonalAttributesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonalAttributesTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonalAttributesController controller;

    @Mock
    private PersonalAttributesService service;


    ObjectMapper om = new ObjectMapper();
    User user = new User();
    PersonalAttributesDTO attributesDTO = new PersonalAttributesDTO();

    @BeforeEach
    public void setup() {   //Intialize mocks with openMocks, creating a mock controller
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");

        attributesDTO.setReligion("Islam");
        attributesDTO.setBirthPlace("Chittagong");
        attributesDTO.setNationality("Bangladeshi");
        attributesDTO.setBloodGroup("O+");
    }

    @Test
    public void createAttributesTest() throws Exception {

        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("Create Attribute Success", true, null);

        Mockito.when(service.create(attributesDTO, user.getId())).thenReturn(responseDTO);// initialize service with expected response

        String jsonRequest = om.writeValueAsString(attributesDTO);  // payload
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/1/attribute-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Create Attribute Success"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void updateAttributesTest() throws Exception {

        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("Update Attribute Success", true, null);

        Mockito.when(service.update(attributesDTO, user.getId())).thenReturn(responseDTO);// initialize service with expected response

        String jsonRequest = om.writeValueAsString(attributesDTO);  // payload
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/attribute-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Update Attribute Success"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void getAttributesTest() throws Exception {
        PersonalAttributes attributes = PersonalAttributesDTO.getPersonalAttributesEntity(attributesDTO);
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("Personal Attribute found",
                true,
                attributes);//Expected return

        Mockito.when(service.read(user.getId())).thenReturn(responseDTO);// testing service for read: Controller to Service
                                                                            // [Checking if service is available]

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders  //client to controller-- > just the request object
                .get("/users/1/attribute-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)    //Call controller using request object
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Personal Attribute found"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(attributes));
    }

}
