package com.aes.corebackend.unit.test.controller.personnelmanagement;

import com.aes.corebackend.controller.personnelmanagement.PersonalAddressController;
import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.aes.corebackend.util.response.APIResponseStatus.*;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;


public class PersonalAddressInformationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonalAddressController addressController;

    @Mock
    private PersonalAddressService addressService;

    private ObjectMapper om = new ObjectMapper();
    private User user = new User();
    private PersonalAddressInfoDTO addressInfoDTO = new PersonalAddressInfoDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(addressController)
                .build();

        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");

        addressInfoDTO.setPermanentAddress("Banani");
        addressInfoDTO.setPresentAddress("Motijheel");
    }

    @Test
    @DisplayName("create address record - success")
    public void createAddressSuccessTest() throws Exception {
        expectedResponse.setResponse(ADDRESS_CREATE_SUCCESS, TRUE, null, SUCCESS);
        /** initialize service with expected response*/
        Mockito.when(addressService.create(addressInfoDTO, user.getId())).thenReturn(expectedResponse);

        String jsonRequest = om.writeValueAsString(addressInfoDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/personal-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    @Test
    @DisplayName("create address record - failure - DTO validation error")
    public void createAddressFailureDTOValidationErrorAnd400BadRequestTest() throws Exception {
        expectedResponse.setResponse(ADDRESS_CREATE_FAIL, FALSE, null, ERROR);
        /** initialize service with expected response*/
        Mockito.when(addressService.create(addressInfoDTO, user.getId())).thenReturn(expectedResponse);
        /** update dto with invalid value */
        addressInfoDTO.setPresentAddress("123");

        String jsonRequest = om.writeValueAsString(addressInfoDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1/personal-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                /** when dto validation fails we receive bad request 400 as api response*/
                .andExpect(status().isBadRequest())
                //TODO need to set when/before building fieldErrors
//                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors").isNotEmpty())
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    //TODO test case for user not found
    //TODO test case for userId validation error (string instead of integer id)

    @Test
    @DisplayName("update address record - success")
    public void updateAddressSuccessTest() throws Exception {
        expectedResponse.setResponse(ADDRESS_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        addressInfoDTO.setPresentAddress("Gulshan");
        /** initialize service with expected response*/
        Mockito.when(addressService.update(addressInfoDTO, user.getId())).thenReturn(expectedResponse);

        String jsonRequest = om.writeValueAsString(addressInfoDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/personal-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    @Test
    @DisplayName("update address record - failure - DTO validation error")
    public void updateAddressFailureDTOValidationErrorAnd400BadRequestTest() throws Exception {
        expectedResponse.setResponse(ADDRESS_UPDATE_FAIL, FALSE, null, ERROR);
        /** update dto with invalid value */
        addressInfoDTO.setPresentAddress("123");
        /** initialize service with expected response*/
        Mockito.when(addressService.update(addressInfoDTO, user.getId())).thenReturn(expectedResponse);

        String jsonRequest = om.writeValueAsString(addressInfoDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1/personal-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                //TODO message need to be set
//                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.fieldsErrors").isNotEmpty())
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    @Test
    @DisplayName("read address record - success")
    public void readAddressSuccessTest() throws Exception {
        PersonalAddressInfo addressEntity = PersonalAddressInfoDTO.getPersonalAddressInfoEntity(addressInfoDTO);
        expectedResponse.setResponse(ADDRESS_RECORD_FOUND, TRUE, addressEntity, SUCCESS);

        /** testing service for read: Controller to Service */
        /** [Checking if service is available] */
        Mockito.when(addressService.read(user.getId())).thenReturn(expectedResponse);

        /** client to controller-- > just the request object */
        /** Call controller using request object */
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1/personal-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedResponse.getMessage()))
                .andExpect(jsonPath("$.success").value(expectedResponse.isSuccess()))
                .andExpect(jsonPath("$.data").value(expectedResponse.getData()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().toString()));
    }

    @Test
    @DisplayName("read address record - failure - path variable validation error")
    public void readAddressFailurePathVariableUserIdValidationErrorAnd400BadRequestTest() throws Exception {
        expectedResponse.setResponse(ADDRESS_RECORD_NOT_FOUND, FALSE, null, ERROR);

        /** testing service for read: Controller to Service */
        /** [Checking if service is available] */
        Mockito.when(addressService.read(user.getId())).thenReturn(expectedResponse);

        /** client to controller-- > just the request object */
        /** Call controller using request object */
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/abc/personal-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //TODO more to look into this - path variable validation
    }
}
