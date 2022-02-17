package com.aes.corebackend.integrationtest.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;
import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.repository.personnelmanagement.PersonalEducationRepository;
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

import static com.aes.corebackend.util.response.APIResponseStatus.ERROR;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.TRUE;
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
public class PersonalEducationControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PersonalEducationRepository educationRepository;

    private static String USERNAME = "012518";
    private static String TOKEN = "";
    private UserDetails userDetails;

    private APIResponse expectedResponse = APIResponse.getApiResponse();
    private PersonalEducationDTO personalEducationDTO = new PersonalEducationDTO();

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts", null, null, null, null);
    private PersonalEducationInfo personalEducationInfoSSC = new PersonalEducationInfo(1L, "SSC", "ABC High School", 5.00f, 4.90f, "2015", user);
    private PersonalEducationInfo personalEducationInfoHSC = new PersonalEducationInfo(2L, "HSC", "ABC College", 5.00f, 4.90f, "2017", user);
    private PersonalEducationInfo personalEducationInfoBSC = new PersonalEducationInfo(3L, "BSC", "ABC University", 4.00f, 3.90f, "2021", user);

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        TOKEN = jwtTokenUtil.generateToken(userDetails);

        personalEducationDTO.setDegreeName("SSC");
        personalEducationDTO.setInstitutionName("ABC High School");
        personalEducationDTO.setGpaScale(5.00f);
        personalEducationDTO.setCgpa(4.90f);
        personalEducationDTO.setPassingYear("2015");
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void createPersonalEducationSuccessTest() throws Exception {

        expectedResponse.setResponse(EDUCATION_CREATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(post("/users/1/education-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(personalEducationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void updatePersonalEducationSuccessTest() throws Exception {

        personalEducationDTO.setInstitutionName("ABC School");
        expectedResponse.setResponse(EDUCATION_UPDATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(put("/users/1/education-information/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(personalEducationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void getPersonalEducationInformationListSuccessTest() throws Exception {

        ArrayList<PersonalEducationInfo> educationInfos = educationRepository.findPersonalEducationInfoByUserId(1L);
        ArrayList<PersonalEducationDTO> educationDTOs = new ArrayList<>();
        educationInfos.forEach(education -> {
            educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(education));
        });

        expectedResponse.setResponse(EDUCATION_RECORDS_FOUND, TRUE, objectMapper.writeValueAsString(educationDTOs), SUCCESS);

        mockMvc.perform(get("/users/1/education-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void getPersonalEducationSuccessTest() throws Exception {

        PersonalEducationInfo educationInfo = educationRepository.findPersonalEducationInfoByIdAndUserId(1L, 1L);
        expectedResponse.setResponse(EDUCATION_RECORD_FOUND, TRUE, PersonalEducationDTO.getPersonalEducationDTO(educationInfo), SUCCESS);

        mockMvc.perform(get("/users/1/education-information/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void getPersonalEducationNotFoundTest() throws Exception {

        expectedResponse.setResponse(EDUCATION_RECORD_NOT_FOUND, FALSE, null, ERROR);

        mockMvc.perform(get("/users/2/education-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }
}
