package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import com.aes.corebackend.service.personnelmanagement.PersonalEducationService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static com.aes.corebackend.util.response.AjaxResponseStatus.ERROR;
import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonalEducationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonalEducationService personalEducationService;

    @InjectMocks
    private PersonalEducationController personalEducationController;

    private ObjectMapper om = new ObjectMapper();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts", null, null, null, null);
    private PersonalEducationInfo personalEducationInfoSSC = new PersonalEducationInfo(1L, "SSC", "ABC High School", 5.00f, 4.90f, "2015", user);
    private PersonalEducationInfo personalEducationInfoHSC = new PersonalEducationInfo(2L, "HSC", "ABC College", 5.00f, 4.90f, "2017", user);
    private PersonalEducationInfo personalEducationInfoBSC = new PersonalEducationInfo(3L, "BSC", "ABC University", 4.00f, 3.90f, "2021", user);
    private PersonalEducationDTO personalEducationDTO = new PersonalEducationDTO();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(personalEducationController)
                .build();

        personalEducationDTO.setDegreeName("SSC");
        personalEducationDTO.setInstitutionName("ABC High School");
        personalEducationDTO.setGpaScale(5.00f);
        personalEducationDTO.setCgpa(4.90f);
        personalEducationDTO.setPassingYear("2015");
    }

    @Test
    @DisplayName("create education information - success")
    public void createPersonalEducationSuccessTest() throws Exception {

        expectedResponse.setResponse(EDUCATION_CREATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(personalEducationService.create(personalEducationDTO, 1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/education-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(personalEducationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("create education information - validation error - year format incorrect")
    public void createPersonalEducationValidationErrorTest() throws Exception {

        personalEducationDTO.setPassingYear("@0222");
        expectedResponse.setResponse(null, FALSE, null, ERROR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/education-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(personalEducationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors.passingYear").value("Year format incorrect"));
    }

    @Test
    @DisplayName("update education information - success")
    public void updatePersonalEducationSuccessTest() throws Exception {

        personalEducationDTO.setInstitutionName("ABC School");
        expectedResponse.setResponse(EDUCATION_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(personalEducationService.update(personalEducationDTO, 1L, 1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/education-information/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(personalEducationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("update education information - validation error - Please convert GPA Scale to Maximum 5")
    public void updatePersonalEducationValidationErrorTest() throws Exception {

        personalEducationDTO.setGpaScale(6.00f);
        String gpaScaleErrorMessage = "Please convert GPA Scale to Maximum 5";
        expectedResponse.setResponse(null, FALSE, null, ERROR);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/education-information/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(personalEducationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors.gpaScale").value(gpaScaleErrorMessage));
    }

    @Test
    @DisplayName("Get all education records for a user")
    public void getPersonalEducationInformationListSuccessTest() throws Exception {

        ArrayList<PersonalEducationDTO> educationDTOs = new ArrayList<>();
        educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoSSC));
        educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoHSC));
        educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoBSC));

        expectedResponse.setResponse(EDUCATION_RECORDS_FOUND, TRUE, om.writeValueAsString(educationDTOs), SUCCESS);
        Mockito.when(personalEducationService.read(1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/education-information"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("Get a single record")
    public void getPersonalEducationSuccessTest() throws Exception {

        expectedResponse.setResponse(EDUCATION_RECORD_FOUND, TRUE, PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoSSC), SUCCESS);
        Mockito.when(personalEducationService.read(1L, 1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/education-information/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }
}