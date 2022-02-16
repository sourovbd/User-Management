package com.aes.corebackend.unittest.controller.personnelmanagement;

import com.aes.corebackend.controller.personnelmanagement.JobExperienceController;
import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.service.personnelmanagement.PersonalJobExperienceService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.aes.corebackend.util.response.APIResponseStatus.*;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;

public class PersonalJobExperienceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private JobExperienceController jobExperienceController;

    @Mock
    private PersonalJobExperienceService jobExperienceService;

    private ObjectMapper om = new ObjectMapper();
    private User user = new User();
    private PersonalJobExperienceDTO jobExperienceDTO = new PersonalJobExperienceDTO();
    private PersonalJobExperienceDTO jobExperienceDTO2 = new PersonalJobExperienceDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();
    private final DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    @BeforeEach
    public void setup() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(jobExperienceController)
                .build();

        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");

        jobExperienceDTO.setId(1L);
        jobExperienceDTO.setEmployerName("REVE");
        jobExperienceDTO.setDesignation("SDE");
        jobExperienceDTO.setStartDate("12-03-2017");
        jobExperienceDTO.setEndDate("12-12-2020");
        jobExperienceDTO.setResponsibilities("development");

        jobExperienceDTO2.setId(2L);
        jobExperienceDTO2.setEmployerName("Nascenia");
        jobExperienceDTO2.setDesignation("JrDeveloper");
        jobExperienceDTO2.setStartDate("01-10-2015");
        jobExperienceDTO2.setEndDate("12-02-2017");
        jobExperienceDTO2.setResponsibilities("development");
    }

    @Test
    @DisplayName("create job experience record - success")
    public void createJobExperienceSuccessTest() throws Exception {
        expectedResponse.setResponse(JOB_EXPERIENCE_CREATE_SUCCESS, TRUE, null, SUCCESS);
        /** initialize service with expected response*/
        Mockito.when(jobExperienceService.create(jobExperienceDTO, user.getId())).thenReturn(expectedResponse);

        String jsonRequestPayload = om.writeValueAsString(jobExperienceDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/1/job-experiences")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()));
    }

    @Test
    @DisplayName("create job experience record - failure - DTO validation error")
    public void createJobExperienceFailureDTOValidationERRORAnd400BadRequestTest() throws Exception {
        expectedResponse.setResponse(JOB_EXPERIENCE_CREATE_FAIL, FALSE, null, ERROR);
        //TODO catch date parse error and respond
//        jobExperienceDTO.setStartDate(dateFormatter.parse("12/03/2017"));
        jobExperienceDTO.setDesignation("SDE1");
        /** initialize service with expected response*/
        Mockito.when(jobExperienceService.create(jobExperienceDTO, user.getId())).thenReturn(expectedResponse);

        String jsonRequestPayload = om.writeValueAsString(jobExperienceDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/1/job-experiences")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    @Test
    @DisplayName("update job experience record - success")
    public void updateJobExperienceSuccessTest() throws Exception {
        expectedResponse.setResponse(JOB_EXPERIENCE_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        jobExperienceDTO.setResponsibilities("design");
        /** initialize service with expected response*/
        Mockito.when(jobExperienceService.update(jobExperienceDTO, user.getId(), 1L)).thenReturn(expectedResponse);

        String jsonRequestPayload = om.writeValueAsString(jobExperienceDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/job-experiences/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()));
    }

    @Test
    @DisplayName("update job experience record - failure - DTO validation error")
    public void updateJobExperienceFailureDTOValidationERRORAnd400BadRequestTest() throws Exception {
        expectedResponse.setResponse(JOB_EXPERIENCE_UPDATE_FAIL, FALSE, null, ERROR);
        jobExperienceDTO.setResponsibilities("design and architecting"); /** fix space issue*/
        /** initialize service with expected response*/
        Mockito.when(jobExperienceService.update(jobExperienceDTO, user.getId(), 1L)).thenReturn(expectedResponse);

        String jsonRequestPayload = om.writeValueAsString(jobExperienceDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/job-experiences/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                //TODO need to fix message issue
//                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()));
    }

    @Test
    @DisplayName("read single job experience record - success")
    public void readSingleJobExperienceRecordSuccessTest() throws Exception {
        expectedResponse.setResponse(JOB_EXPERIENCE_RECORD_FOUND, TRUE, jobExperienceDTO, SUCCESS);
        /** initialize service with expected response*/
        Mockito.when(jobExperienceService.read(user.getId(), 1L)).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/users/1/job-experiences/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(JOB_EXPERIENCE_RECORD_FOUND))
                .andExpect(jsonPath("$.success").value(TRUE))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(jobExperienceDTO));
    }

    @Test
    @DisplayName("read single job experience record - failure - path variable validation error")
    public void readSingleJobExperienceRecordFailurePathVariableUserIdValidationErrorAnd400BadRequestTest() throws Exception {
        expectedResponse.setResponse(JOB_EXPERIENCE_RECORD_NOT_FOUND, FALSE, null, ERROR);
        /** initialize service with expected response*/
        Mockito.when(jobExperienceService.read(user.getId(), 1L)).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/users/abc/job-experiences/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
//                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
//                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
//                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
//                .andDo(MockMvcResultHandlers.print());
        //TODO catch and respond with validation error
    }

    @Test
    @DisplayName("read multiple job experience record - success")
    public void readMultipleJobExperienceRecordsSuccessTest() throws Exception {
        ArrayList jobExperiences = new ArrayList<>();
        jobExperiences.add(jobExperienceDTO);
        jobExperiences.add(jobExperienceDTO2);

        expectedResponse.setResponse(JOB_EXPERIENCE_RECORDS_FOUND, TRUE, om.writeValueAsString(jobExperiences), SUCCESS);

        /** initialize service with expected response*/
        Mockito.when(jobExperienceService.read(user.getId())).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/users/1/job-experiences")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("read multiple job experience record - failure - path variable validation error")
    public void readMultipleJobExperienceRecordsFailurePathVariableUserIdValidationErrorAnd400BadRequestTest() throws Exception {
        expectedResponse.setResponse(JOB_EXPERIENCE_RECORD_NOT_FOUND, FALSE, null, ERROR);

        /** initialize service with expected response*/
        Mockito.when(jobExperienceService.read(user.getId())).thenReturn(expectedResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/users/1/job-experiences")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
//                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
//                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
//                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
        //TODO catch and respond with validation error
    }
}
