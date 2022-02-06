package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.enumeration.Gender;
import com.aes.corebackend.service.personnelmanagement.PersonalBasicInformationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonalBasicInformationTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonalBasicInfoController basicInfoController;

    @Mock
    private PersonalBasicInformationService basicInformationService;

    ObjectMapper om = new ObjectMapper();
    PersonalAddressInfo address = new PersonalAddressInfo();
    User user = new User();
    PersonalBasicInfo basicInfo = new PersonalBasicInfo();
    PersonalBasicInfoDTO basicInfoDTO = new PersonalBasicInfoDTO();

    @BeforeEach
    public void setup() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(basicInfoController).build();

        user.setId(1L);
        user.setBusinessUnit("AES");
        user.setDesignation("SDE");
        user.setDepartment("Development");
        user.setEmailAddress("jahangir.alam2@anwargroup.net");
        user.setAddress(address);
        user.setEmployeeId("011273");

        basicInfo.setId(1L);
        basicInfo.setFirstName("jahangir");
        basicInfo.setLastName("alam");
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        basicInfo.setDateOfBirth(formatter.parse("12-09-1989"));
        basicInfo.setGender(Gender.MALE);
        basicInfo.setUser(user);

        basicInfoDTO.setFirstName("jahangir");
        basicInfoDTO.setLastName("alam");
        basicInfoDTO.setDateOfBirth(formatter.parse("12-09-1989"));
        basicInfoDTO.setGender(Gender.MALE);
    }

    @Test
    public void createBasicInformationTest() throws Exception {

        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Basic information creation successful", true, null);
        /** initialize service with expected response*/
        Mockito.when(basicInformationService.create(basicInfoDTO, user.getId())).thenReturn(response);

        String jsonRequestPayload = om.writeValueAsString(basicInfoDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/1/basic-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Basic information creation successful"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void updateBasicInformationTest() throws Exception {
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("Basic information update successful", true, null);
        basicInfoDTO.setFirstName("Rifat");

        /** initialize service with expected response*/
        Mockito.when(basicInformationService.update(basicInfoDTO, user.getId())).thenReturn(responseDTO);

        String jsonRequestPayload = om.writeValueAsString(basicInfoDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/basic-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Basic information update successful"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void readBasicInformationTest() throws Exception {
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("Basic information found",
                true,
                basicInfoDTO);

        /** initialize service with expected response*/
        Mockito.when(basicInformationService.read(user.getId())).thenReturn(responseDTO);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/users/1/basic-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Basic information found"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(basicInfoDTO));
    }
}
