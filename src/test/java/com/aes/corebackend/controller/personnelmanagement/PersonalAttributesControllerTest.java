package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.service.personnelmanagement.PersonalAttributesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.aes.corebackend.util.response.APIResponseStatus.*;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;

public class PersonalAttributesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonalAttributesController controller;

    @Mock
    private PersonalAttributesService service;

    private ObjectMapper om = new ObjectMapper();
    private User user = new User();
    private PersonalAttributesDTO attributesDTO = new PersonalAttributesDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    public void setup() {   /** Initialize mocks with openMocks, creating a mock controller */
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
    @DisplayName("Create attributes - success")
    public void createAttributesSuccessTest() throws Exception {

        expectedResponse.setResponse(ATTRIBUTES_CREATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(service.create(attributesDTO, user.getId())).thenReturn(expectedResponse); /** initialize service with expected response */

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/attribute-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(attributesDTO)))  /** payload */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Create attributes - validation error - blood group")
    public void createAttributesValidationErrorTest() throws Exception {

        String bloodGroupErrorMessage = "Blood Group: Invalid";
        attributesDTO.setBloodGroup("F+");
        expectedResponse.setResponse(null, FALSE, null, ERROR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/attribute-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(attributesDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors.bloodGroup").value(bloodGroupErrorMessage));
    }

    @Test
    @DisplayName("Update attributes - success")
    public void updateAttributesSuccessTest() throws Exception {

        attributesDTO.setBloodGroup("AB+");
        expectedResponse.setResponse(ATTRIBUTES_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(service.update(attributesDTO, user.getId())).thenReturn(expectedResponse); /** initialize service with expected response */

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/attribute-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(attributesDTO)))  /** payload */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Update attributes - validation error - nationality")
    public void updateAttributesValidationErrorTest() throws Exception {

        String nationalityErrorMessage = "Nationality: Field cannot have numeric or special characters";
        attributesDTO.setNationality("B@angladeshi");
        expectedResponse.setResponse(null, FALSE, null, ERROR);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/attribute-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(attributesDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors.nationality").value(nationalityErrorMessage));
    }

    @Test
    @DisplayName("Get attributes - success")
    public void getAttributesSuccessTest() throws Exception {

        PersonalAttributes attributes = PersonalAttributesDTO.getPersonalAttributesEntity(attributesDTO);
        expectedResponse.setResponse(ATTRIBUTES_RECORD_FOUND, TRUE, attributes, SUCCESS); /** Expected return */

        Mockito.when(service.read(user.getId())).thenReturn(expectedResponse); /** testing service for read: Controller to Service */
        /** [Checking if service is available] */

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders  /** client to controller-- > just the request object */
                .get("/users/1/attribute-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)    /** Call controller using request object */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

}
