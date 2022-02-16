package com.aes.corebackend.unit.test.controller.personnelmanagement;

import com.aes.corebackend.controller.personnelmanagement.FamilyInformationController;
import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.service.personnelmanagement.FamilyInformationService;

import com.aes.corebackend.util.response.APIResponse;
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

import static com.aes.corebackend.util.response.APIResponseStatus.ERROR;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class FamilyInformationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FamilyInformationService service;

    @InjectMocks
    private FamilyInformationController controller;

    private ObjectMapper om = new ObjectMapper();
    private User user = new User();
    private PersonalFamilyInfoDTO familyDTO = new PersonalFamilyInfoDTO();
    private PersonalFamilyInfo personalFamilyInfo = new PersonalFamilyInfo();
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

        familyDTO.setSpouseName("Arham Amani Ahmed");
        familyDTO.setMothersName("Haseena Parveen Manwar");
        familyDTO.setFathersName("Manwar Hossain");
        familyDTO.setMaritalStatus("Married");

        personalFamilyInfo.setId(1L);
        personalFamilyInfo.setSpouseName("Arham Amani Ahmed");
        personalFamilyInfo.setMothersName("Haseena Parveen Manwar");
        personalFamilyInfo.setFathersName("Manwar Hossain");
        personalFamilyInfo.setMaritalStatus("Married");
        personalFamilyInfo.setUser(user);
    }

    @Test
    @DisplayName("Create family information - success")
    public void createFamilyInformationSuccessTest() throws Exception {

        expectedResponse.setResponse(FAMILY_CREATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(service.create(familyDTO, user.getId())).thenReturn(expectedResponse); /** initialize service with expected response */

        mockMvc.perform(post("/users/1/family-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(familyDTO))) /** payload */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Create family information - validation error - maritalStatus")
    public void createFamilyInformationValidationErrorTest() throws Exception {

        String maritalStatusErrorMessage = "Marital Status: Field cannot have numeric or special characters";
        familyDTO.setMaritalStatus("M@rried");
        expectedResponse.setResponse(null, FALSE, null, ERROR);

        mockMvc.perform(post("/users/1/family-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(familyDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors.maritalStatus").value(maritalStatusErrorMessage));
    }

    @Test
    @DisplayName("Update family information - success")
    public void updateAttributesSuccessTest() throws Exception {

        expectedResponse.setResponse(FAMILY_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        familyDTO.setMothersName("Neela Manwar");
        Mockito.when(service.update(familyDTO, user.getId())).thenReturn(expectedResponse); /** initialize service with expected response */

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/family-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(familyDTO)); /** payload */

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Update family information - validation error - mothersName")
    public void updateAttributesValidationErrorTest() throws Exception {

        String motherNameErrorMessage = "Mother's Name: Field cannot have numeric or special characters";
        familyDTO.setMothersName("@bc");
        expectedResponse.setResponse(null, FALSE, null, ERROR);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/family-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(familyDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors.mothersName").value(motherNameErrorMessage));
    }

    @Test
    @DisplayName("Get family information - success")
    public void getFamilyInfoSuccessTest() throws Exception {

        expectedResponse.setResponse(FAMILY_RECORD_FOUND, TRUE, PersonalFamilyInfoDTO.getPersonalFamilyDTO(personalFamilyInfo), SUCCESS); /** Expected return */

        Mockito.when(service.read(user.getId())).thenReturn(expectedResponse); /** testing service for read: Controller to Service */
        /** [Checking if service is available] */

        MockHttpServletRequestBuilder mockRequest = get("/users/1/family-information");

        mockMvc.perform(mockRequest)    /** Call controller using request object */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Get Family information - record not found ")
    public void getFamilyInfoRecordNotFoundTest() throws Exception {

        expectedResponse.setResponse(FAMILY_RECORD_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(service.read(Mockito.anyLong())).thenReturn(expectedResponse);

        mockMvc.perform(get("/users/2/family-information"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }
}