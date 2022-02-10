package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.controller.personnelmanagement.PersonalEducationController;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import com.aes.corebackend.service.personnelmanagement.PersonalEducationService;
import com.aes.corebackend.util.response.AjaxResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonalEducationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonalEducationService personalEducationService;

    @InjectMocks
    private PersonalEducationController personalEducationController;

    ObjectMapper om = new ObjectMapper();

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts", null, null, null, null);
    private PersonalEducationInfo personalEducationInfoSSC = new PersonalEducationInfo(1L, "SSC", "ABC High School", 5.00f, 4.90f, "2015", user);
    private PersonalEducationInfo personalEducationInfoHSC = new PersonalEducationInfo(2L, "HSC", "ABC College", 5.00f, 4.90f, "2017", user);
    private PersonalEducationInfo personalEducationInfoBSC = new PersonalEducationInfo(3L, "BSC", "ABC University", 4.00f, 3.90f, "2021", user);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(personalEducationController)
                .build();
    }

    @Test
    public void getPersonalEducationInformationsTest() throws Exception {
        ArrayList<PersonalEducationDTO> educationDTOs = new ArrayList<>();
        educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoSSC));
        educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoHSC));
        educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoBSC));

        APIResponse expectedResponse = new APIResponse(EDUCATION_RECORDS_FOUND, true, om.writeValueAsString(educationDTOs), SUCCESS);
        Mockito.when(personalEducationService.read(1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/education-information"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    public void getPersonalEducationTest() throws Exception {

        APIResponse expectedResponse = new APIResponse(EDUCATION_RECORD_FOUND, TRUE, PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoSSC), SUCCESS);
        Mockito.when(personalEducationService.read(1L, 1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/education-information/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }
}