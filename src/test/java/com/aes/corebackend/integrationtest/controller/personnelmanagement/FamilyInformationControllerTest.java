package com.aes.corebackend.integrationtest.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalFamilyInfoRepository;
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
import org.springframework.test.web.servlet.MvcResult;

import static com.aes.corebackend.util.response.APIResponseStatus.ERROR;
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
public class FamilyInformationControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PersonalFamilyInfoRepository familyInfoRepository;

    private static String USERNAME = "012518";
    private static String TOKEN = "";
    private UserDetails userDetails;

    private APIResponse expectedResponse = APIResponse.getApiResponse();
    private PersonalFamilyInfoDTO familyDTO = new PersonalFamilyInfoDTO();

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        TOKEN = jwtTokenUtil.generateToken(userDetails);

        familyDTO.setFathersName("Mr test");
        familyDTO.setMothersName("Mrs Test");
        familyDTO.setMaritalStatus("Married");
        familyDTO.setSpouseName("Test name");
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void createFamilyInfoSuccessTest() throws Exception {

        expectedResponse.setResponse(FAMILY_CREATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(post("/users/2/family-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(familyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void createFamilyInfoFailedTest() throws Exception {

        expectedResponse.setResponse(FAMILY_CREATE_FAIL, FALSE, null, ERROR);

        mockMvc.perform(post("/users/1/family-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(familyDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void updateFamilyInfoSuccessTest() throws Exception {

        PersonalFamilyInfoDTO existingFamilyDTO = PersonalFamilyInfoDTO.getPersonalFamilyDTO(familyInfoRepository.findPersonalFamilyInfoByUserId(1L));
        existingFamilyDTO.setMaritalStatus("Married");
        existingFamilyDTO.setSpouseName("Mrs Doe");
        expectedResponse.setResponse(FAMILY_UPDATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(put("/users/1/family-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingFamilyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void getFamilyInfoSuccessTest() throws Exception {

        PersonalFamilyInfo existingFamilyInfo = familyInfoRepository.findPersonalFamilyInfoByUserId(1L);
        expectedResponse.setResponse(FAMILY_RECORD_FOUND, TRUE, PersonalFamilyInfoDTO.getPersonalFamilyDTO(existingFamilyInfo), SUCCESS);

        mockMvc.perform(get("/users/1/family-information")
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
    public void getFamilyInfoRecordNotFoundTest() throws Exception {

        expectedResponse.setResponse(FAMILY_RECORD_NOT_FOUND, FALSE, null, ERROR);

        mockMvc.perform(get("/users/2/family-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }
}
