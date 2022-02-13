package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.enumeration.Gender;
import com.aes.corebackend.service.personnelmanagement.PersonalBasicInformationService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonalBasicInformationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonalBasicInfoController basicInfoController;

    @Mock
    private PersonalBasicInformationService basicInformationService;

    private ObjectMapper om = new ObjectMapper();
    private PersonalAddressInfo address = new PersonalAddressInfo();
    private User user = new User();
    private PersonalBasicInfo basicInfo = new PersonalBasicInfo();
    private PersonalBasicInfoDTO basicInfoDTO = new PersonalBasicInfoDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

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
    @DisplayName("create basic info record - success")
    public void createBasicInformationSuccessTest() throws Exception {
        expectedResponse.setResponse(BASIC_INFORMATION_CREATE_SUCCESS, TRUE, null, SUCCESS);
        /** initialize service with expected response*/
        Mockito.when(basicInformationService.create(basicInfoDTO, user.getId())).thenReturn(expectedResponse);

        String jsonRequestPayload = om.writeValueAsString(basicInfoDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/basic-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    @Test
    @DisplayName("update basic info record - success")
    public void updateBasicInformationSuccessTest() throws Exception {
        expectedResponse.setResponse(BASIC_INFORMATION_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        basicInfoDTO.setFirstName("Rifat");
        /** initialize service with expected response*/
        Mockito.when(basicInformationService.update(basicInfoDTO, user.getId())).thenReturn(expectedResponse);

        String jsonRequestPayload = om.writeValueAsString(basicInfoDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/basic-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    @Test
    @DisplayName("read basic info record - success")
    public void readBasicInformationSuccessTest() throws Exception {
        expectedResponse.setResponse(BASIC_INFORMATION_RECORD_FOUND, TRUE, basicInfoDTO, SUCCESS);
        /** initialize service with expected response*/
        Mockito.when(basicInformationService.read(user.getId())).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/basic-information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }
}
