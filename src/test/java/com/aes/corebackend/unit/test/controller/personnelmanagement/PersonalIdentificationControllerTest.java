package com.aes.corebackend.unit.test.controller.personnelmanagement;

import com.aes.corebackend.controller.personnelmanagement.PersonalIdentificationController;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalIdentificationInfoDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
import com.aes.corebackend.service.personnelmanagement.PersonalIdentificationService;
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

public class PersonalIdentificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonalIdentificationController controller;

    @Mock
    private PersonalIdentificationService service;

    private ObjectMapper om = new ObjectMapper();
    private User user = new User();
    private PersonalIdentificationInfoDTO idDTO = new PersonalIdentificationInfoDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    public void setup() {   /** Initialize mocks with openMocks, creating a mock controller */
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
    @DisplayName("Create Identification Information - Success")
    public void createIdentificationSuccessTest() throws Exception {

        expectedResponse.setResponse(IDENTIFICATION_CREATE_SUCCESS, TRUE, null, SUCCESS); /** This is the expected value as well wierdly */
        Mockito.when(service.create(idDTO, user.getId())).thenReturn(expectedResponse); /** initialize service with expected response */

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/identification-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(idDTO))) /** Payload */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Create Identification Information - Validation Error - etin")
    public void createIdentificationValidationErrorTest() throws Exception {

        String etinErrorMessage = "E-Tin: Field cannot have alphabetic or special characters";
        idDTO.setEtin("0101010101a@");
        expectedResponse.setResponse(null, FALSE, null, ERROR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/identification-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(idDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors.etin").value(etinErrorMessage));
    }

    @Test
    @DisplayName("Update Identification Information - Success")
    public void updateIdentificationSuccessTest() throws Exception {

        idDTO.setNationalID("0103567322");
        expectedResponse.setResponse(IDENTIFICATION_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(service.update(idDTO, user.getId())).thenReturn(expectedResponse); /** initialize service with expected response */

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/identification-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(idDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Update Identification Information - Validation Error - national ID")
    public void updateIdentificationValidationErrorTest() throws Exception {

        idDTO.setNationalID("0123456789@a");
        String nationalIDErrorMessage = "NationalID: Field cannot have alphabetic or special characters";
        expectedResponse.setResponse(null, FALSE, null, ERROR);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/identification-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(idDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors.nationalID").value(nationalIDErrorMessage));
    }

    @Test
    @DisplayName("get Identification information - success")
    public void getIdentificationSuccessTest() throws Exception {
        PersonalIdentificationInfo id = PersonalIdentificationInfoDTO.getPersonalIdentificationEntity(idDTO);
        expectedResponse.setResponse(IDENTIFICATION_RECORD_FOUND, TRUE, id, SUCCESS); /** Expected return */

        Mockito.when(service.read(user.getId())).thenReturn(expectedResponse); /** testing service for read: Controller to Service */
        /** [Checking if service is available] */

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders  /** client to controller-- > just the request object */
                .get("/users/1/identification-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)    /** Call controller using request object */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Get Identification information - record not found")
    public void getIdentificationRecordNotFoundTest() throws Exception {

        expectedResponse.setResponse(IDENTIFICATION_RECORD_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(service.read(user.getId())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/identification-information"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }
}
