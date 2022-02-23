package com.aes.corebackend.integrationtest.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.repository.personnelmanagement.PersonalAddressInfoRepository;
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
public class PersonalAddressControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PersonalAddressInfoRepository addressInfoRepository;

    private APIResponse expectedResponse = APIResponse.getApiResponse();
    private PersonalAddressInfoDTO addressInfoDTO = new PersonalAddressInfoDTO();

    private static String USERNAME = "012518";
    private static String TOKEN = "";
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        TOKEN = jwtTokenUtil.generateToken(userDetails);

        addressInfoDTO.setPresentAddress("MotijheelDhaka");
        addressInfoDTO.setPermanentAddress("DhanmondiDhaka");
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void createAddressInfoSuccessTest() throws Exception {

        expectedResponse.setResponse(ADDRESS_CREATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(post("/users/2/personal-address")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addressInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void createAddressInfoFailedTest() throws Exception {

        expectedResponse.setResponse(ADDRESS_CREATE_FAIL, FALSE, null, ERROR);

        mockMvc.perform(post("/users/1/personal-address")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addressInfoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void updateAddressInfoFailedTest() throws Exception {

        PersonalAddressInfoDTO existingAddressDTO = PersonalAddressInfoDTO.getPersonalAddressInfoDTO(addressInfoRepository.findPersonalAddressInfoByUserId(1L));
        existingAddressDTO.setPermanentAddress(addressInfoDTO.getPermanentAddress());
        existingAddressDTO.setPresentAddress(addressInfoDTO.getPresentAddress());

        expectedResponse.setResponse(ADDRESS_UPDATE_SUCCESS, TRUE, null, SUCCESS);

        mockMvc.perform(put("/users/1/personal-address")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(existingAddressDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }

    @Test
    @DatabaseSetup("/dataset/personnel_management.xml")
    public void getAddressInfoSuccessTest() throws Exception {

        PersonalAddressInfoDTO existingAddressDTO = PersonalAddressInfoDTO.getPersonalAddressInfoDTO(addressInfoRepository.findPersonalAddressInfoByUserId(1L));
        expectedResponse.setResponse(ADDRESS_RECORD_FOUND, TRUE, existingAddressDTO, SUCCESS);

        mockMvc.perform(get("/users/1/personal-address")
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
    public void getAddressInfoNoRecordFoundTest() throws Exception {

        expectedResponse.setResponse(ADDRESS_RECORD_NOT_FOUND, FALSE, null, ERROR);

        mockMvc.perform(get("/users/2/personal-address")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()));
    }
}
