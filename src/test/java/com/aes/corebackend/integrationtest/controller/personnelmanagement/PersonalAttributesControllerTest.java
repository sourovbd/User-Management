package com.aes.corebackend.integrationtest.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.repository.personnelmanagement.PersonalAttributesRepository;
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
public class PersonalAttributesControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PersonalAttributesRepository attributesRepository;

    private APIResponse expectedResponse = APIResponse.getApiResponse();
    private PersonalAttributesDTO attributesDTO = new PersonalAttributesDTO();

    private static String USERNAME = "012518";
    private static String TOKEN = "";
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        TOKEN = jwtTokenUtil.generateToken(userDetails);

        attributesDTO.setReligion("Islam");
        attributesDTO.setBirthPlace("Chittagong");
        attributesDTO.setNationality("Bangladeshi");
        attributesDTO.setBloodGroup("O+");
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void createAttributesSuccessTest() throws Exception {

        expectedResponse.setResponse(ATTRIBUTES_CREATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(post("/users/2/attribute-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(attributesDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void createAttributesFailedTest() throws Exception {

        attributesDTO.setBloodGroup("O+");
        expectedResponse.setResponse(ATTRIBUTES_CREATE_FAIL, FALSE, null, ERROR);

        mockMvc.perform(post("/users/1/attribute-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(attributesDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void updateAttributesSuccessTest() throws Exception {

        PersonalAttributesDTO existingAttributesDTO = PersonalAttributesDTO.getPersonalAttributesDTO(attributesRepository.findPersonalAttributesByUserId(1L));
        existingAttributesDTO.setBloodGroup("AB+");
        expectedResponse.setResponse(ATTRIBUTES_UPDATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(put("/users/1/attribute-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(existingAttributesDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void getAttributesSuccessTest() throws Exception {

        PersonalAttributes existingAttributes = attributesRepository.findPersonalAttributesByUserId(1L);
        expectedResponse.setResponse(ATTRIBUTES_RECORD_FOUND, TRUE, PersonalAttributesDTO.getPersonalAttributesDTO(existingAttributes), SUCCESS);

        mockMvc.perform(get("/users/1/attribute-information")
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
    public void getAttributesNotFoundTest() throws Exception {

        expectedResponse.setResponse(ATTRIBUTES_RECORD_NOT_FOUND, FALSE, null, ERROR);

        mockMvc.perform(get("/users/2/attribute-information")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

}
