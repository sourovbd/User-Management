package com.aes.corebackend.integrationtest.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.repository.personnelmanagement.PersonalJobExperienceRepository;
import com.aes.corebackend.service.springsecurity.CustomUserDetailsService;
import com.aes.corebackend.util.JwtUtil;
import com.aes.corebackend.util.response.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.time.LocalDate;
import java.util.ArrayList;

import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static com.aes.corebackend.util.response.APIResponseStatus.*;
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
public class PersonalJobExperienceControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PersonalJobExperienceRepository personalJobExperienceRepository;

    private static String USERNAME = "012518";
    private static String TOKEN = "";
    private UserDetails userDetails;

    private ObjectMapper om = new ObjectMapper();
    private APIResponse expectedResponse = APIResponse.getApiResponse();
    private PersonalJobExperienceDTO personalJobExperienceDTO = new PersonalJobExperienceDTO();

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts", null, null);
    private PersonalJobExperience personalJobExperienceREVE = new PersonalJobExperience(
            1L,
            "REVE",
            LocalDate.of(2017, 1, 4),
            LocalDate.of(2020, 1, 4),
            "SDE",
            "responsibilities", user
    );
    private PersonalJobExperience personalJobExperienceNascenia = new PersonalJobExperience(
            1L,
            "Nascenia",
            LocalDate.of(2015, 1, 4),
            LocalDate.of(2017, 1, 4),
            "SDE",
            "responsibilities", user
    );

    private PersonalJobExperienceDTO jobExperienceDTO1 = new PersonalJobExperienceDTO();
    private PersonalJobExperienceDTO jobExperienceDTO2 = new PersonalJobExperienceDTO();

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        TOKEN = jwtTokenUtil.generateToken(userDetails);

        personalJobExperienceDTO.setEmployerName("MS");
        personalJobExperienceDTO.setStartDate("12-03-2017");
        personalJobExperienceDTO.setEndDate("12-03-2018");
        personalJobExperienceDTO.setDesignation("SDE");
        personalJobExperienceDTO.setResponsibilities("responsibilities");

        jobExperienceDTO1.setId(1L);
        jobExperienceDTO1.setEmployerName("REVE");
        jobExperienceDTO1.setStartDate("12-03-2017");
        jobExperienceDTO1.setEndDate("12-09-2020");
        jobExperienceDTO1.setDesignation("SDE");
        jobExperienceDTO1.setResponsibilities("Development");

        jobExperienceDTO2.setId(2L);
        jobExperienceDTO2.setEmployerName("Nascenia");
        jobExperienceDTO2.setStartDate("12-03-2017");
        jobExperienceDTO2.setEndDate("12-09-2020");
        jobExperienceDTO2.setDesignation("SDE");
        jobExperienceDTO2.setResponsibilities("Development");
    }

    @Test
    @DisplayName("job experience create - success")
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/user_credentials.xml")
    @DatabaseSetup("/dataset/personal_job_experience.xml")
    public void createJobExperienceSuccessTest() throws Exception {

        expectedResponse.setResponse(JOB_EXPERIENCE_CREATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(post("/users/1/job-experiences")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(personalJobExperienceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("update job experience record - success")
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/user_credentials.xml")
    @DatabaseSetup("/dataset/personal_job_experience.xml")
    public void updateJobExperienceSuccessTest() throws Exception {
        PersonalJobExperienceDTO existingJobExperience = PersonalJobExperienceDTO.getPersonalJobExperienceDTO(personalJobExperienceRepository.findPersonalJobExperienceById(1L));
        existingJobExperience.setEmployerName("NewEmployerName");

        expectedResponse.setResponse(JOB_EXPERIENCE_UPDATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(put("/users/1/job-experiences/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(existingJobExperience)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("read single job experience record - success")
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/user_credentials.xml")
    @DatabaseSetup("/dataset/personal_job_experience.xml")
    public void readSingleJobExperienceSuccessTest() throws Exception {

        PersonalJobExperienceDTO existingJobExperience = PersonalJobExperienceDTO.getPersonalJobExperienceDTO(personalJobExperienceRepository.findPersonalJobExperienceById(1L));
        expectedResponse.setResponse(JOB_EXPERIENCE_RECORD_FOUND, TRUE, existingJobExperience, SUCCESS);

        mockMvc.perform(get("/users/1/job-experiences/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DisplayName("read multiple job experience record - success")
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseSetup("/dataset/user_credentials.xml")
    @DatabaseSetup("/dataset/personal_job_experience.xml")
    public void readMultipleJobExperienceRecordsSuccessTest() throws Exception {
        ArrayList jobExperienceList = new ArrayList<>();
        jobExperienceList.add(jobExperienceDTO1);
        jobExperienceList.add(jobExperienceDTO2);

        expectedResponse.setResponse(JOB_EXPERIENCE_RECORD_FOUND, TRUE, jobExperienceList, SUCCESS);

        mockMvc.perform(get("/users/1/job-experiences")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data[0].employerName").value(jobExperienceDTO1.getEmployerName()));
    }
}
