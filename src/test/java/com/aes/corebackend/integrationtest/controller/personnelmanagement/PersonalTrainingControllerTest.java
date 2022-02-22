package com.aes.corebackend.integrationtest.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalTrainingRepository;
import com.aes.corebackend.service.springsecurity.CustomUserDetailsService;
import com.aes.corebackend.util.JwtUtil;
import com.aes.corebackend.util.response.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class PersonalTrainingControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PersonalTrainingRepository trainingRepository;

    private static final String USERNAME = "012518";
    private static String TOKEN = "";
    private UserDetails userDetails;

    private APIResponse expectedResponse = APIResponse.getApiResponse();
    private PersonalTrainingDTO trainingDTO = new PersonalTrainingDTO();

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        TOKEN = jwtTokenUtil.generateToken(userDetails);

        trainingDTO.setProgramName("Java");
        trainingDTO.setTrainingInstitute("Coursera");
        trainingDTO.setDescription("LearnJava");
        trainingDTO.setStartDate("01-01-2022");
        trainingDTO.setEndDate("31-01-2022");
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void createPersonalTrainingSuccessTest() throws Exception {

        expectedResponse.setResponse(TRAINING_CREATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(post("/users/2/trainings")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(trainingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));

    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void updatePersonalTrainingSuccessTest() throws Exception {

        PersonalTrainingDTO existingTrainingDTO = PersonalTrainingDTO.getPersonalTrainingDTO(trainingRepository.findPersonalTrainingInfoByIdAndUserId(1L, 1L));
        existingTrainingDTO.setDescription("TestDescription");
        expectedResponse.setResponse(TRAINING_UPDATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(put("/users/1/trainings/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(existingTrainingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void getPersonalTrainingListSuccessTest() throws Exception {

        ArrayList<PersonalTrainingInfo> trainingInfos = trainingRepository.findPersonalTrainingInfosByUserId(1L);
        ArrayList<PersonalTrainingDTO> trainingDTOS = new ArrayList<>();
        trainingInfos.forEach(trainingInfo -> {
            trainingDTOS.add(PersonalTrainingDTO.getPersonalTrainingDTO(trainingInfo));
        });

        expectedResponse.setResponse(TRAINING_RECORDS_FOUND, TRUE, trainingDTOS, SUCCESS);

        mockMvc.perform(get("/users/1/trainings")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
                //TODO compare data
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void getSinglePersonalTrainingSuccessTest() throws Exception {

        PersonalTrainingDTO existingTrainingDTO = PersonalTrainingDTO.getPersonalTrainingDTO(trainingRepository.findPersonalTrainingInfoByIdAndUserId(1L,1L));
        expectedResponse.setResponse(TRAINING_RECORD_FOUND, TRUE, existingTrainingDTO, SUCCESS);

        mockMvc.perform(get("/users/1/trainings/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));

    }
}
