package com.aes.corebackend.unit.test.controller.personnelmanagement;

import com.aes.corebackend.controller.personnelmanagement.PersonalTrainingController;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import com.aes.corebackend.service.personnelmanagement.PersonalTrainingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;

public class PersonalTrainingTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonalTrainingService personalTrainingService;

    @InjectMocks
    private PersonalTrainingController personalTrainingController;

    private ObjectMapper om = new ObjectMapper();

    private User user = new User();
    private PersonalTrainingInfo personalTrainingInfo1 = new PersonalTrainingInfo();
    private PersonalTrainingInfo personalTrainingInfo2 = new PersonalTrainingInfo();
    private PersonalTrainingDTO personalTrainingDTO = new PersonalTrainingDTO();

    private final DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    @BeforeEach
    public void setup() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(personalTrainingController)
                .build();

        personalTrainingSetup();
    }

    @Test
    @Disabled
    public void createPersonalTrainingTest() throws Exception {

        APIResponse expectedResponse = new APIResponse();
        expectedResponse.setResponse(TRAINING_CREATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(personalTrainingService.create(personalTrainingDTO, 1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(personalTrainingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @Disabled
    public void updatePersonalTrainingTest() throws Exception {

        APIResponse expectedResponse = new APIResponse();
        expectedResponse.setResponse(TRAINING_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(personalTrainingService.update(personalTrainingDTO, 1L, 1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/trainings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(personalTrainingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    public void getPersonalTrainingsTest() throws Exception {

        ArrayList<PersonalTrainingDTO> trainingDTOs = new ArrayList<>();
        trainingDTOs.add(PersonalTrainingDTO.getPersonalTrainingDTO(personalTrainingInfo1));
        trainingDTOs.add(PersonalTrainingDTO.getPersonalTrainingDTO(personalTrainingInfo2));

        APIResponse expectedResponse = new APIResponse();
        expectedResponse.setResponse(TRAINING_RECORDS_FOUND, TRUE, om.writeValueAsString(trainingDTOs), SUCCESS);
        Mockito.when(personalTrainingService.read(1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    public void getPersonalTrainingTest() throws Exception {

        APIResponse expectedResponse = new APIResponse();
        expectedResponse.setResponse(TRAINING_RECORD_FOUND, TRUE, personalTrainingInfo1, SUCCESS);
        Mockito.when(personalTrainingService.read(1L, 1L)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/trainings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    private void personalTrainingSetup() throws ParseException {
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");

        personalTrainingDTO.setProgramName("TestProgram");
        personalTrainingDTO.setTrainingInstitute("TestInstitute");
        personalTrainingDTO.setDescription("TestDescription");
        personalTrainingDTO.setStartDate(dateFormatter.parse("01-01-2022"));
        personalTrainingDTO.setEndDate(dateFormatter.parse("31-01-2022"));

        personalTrainingInfo1.setId(1L);
        personalTrainingInfo1.setProgramName("Test program");
        personalTrainingInfo1.setTrainingInstitute("Test Institute");
        personalTrainingInfo1.setDescription("Test Description");
        personalTrainingInfo1.setStartDate(dateFormatter.parse("01-01-2022"));
        personalTrainingInfo1.setEndDate(dateFormatter.parse("31-01-2022"));
        personalTrainingInfo1.setUser(user);

        personalTrainingInfo2.setId(2L);
        personalTrainingInfo2.setProgramName("Test_02 program");
        personalTrainingInfo2.setTrainingInstitute("Test_02 Institute");
        personalTrainingInfo2.setDescription("Test_02 Description");
        personalTrainingInfo2.setStartDate(dateFormatter.parse("01-02-2022"));
        personalTrainingInfo2.setEndDate(dateFormatter.parse("28-02-2022"));
        personalTrainingInfo2.setUser(user);
    }
}
