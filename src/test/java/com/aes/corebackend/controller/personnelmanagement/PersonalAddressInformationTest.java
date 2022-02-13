package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.util.response.PMAPIResponseMessage;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonalAddressInformationTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonalAddressController addressController;

    @Mock
    private PersonalAddressService addressService;

    ObjectMapper om = new ObjectMapper();
    User user = new User();
    PersonalAddressInfoDTO addressInfoDTO = new PersonalAddressInfoDTO();
    APIResponse response = APIResponse.getApiResponse();

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
    public void createAddressTest() throws Exception {
        response.setMessage(PMAPIResponseMessage.ADDRESS_CREATE_SUCCESS);
        response.setSuccess(true);

        /** initialize service with expected response*/
        Mockito.when(addressService.create(addressInfoDTO, user.getId())).thenReturn(response);

        String jsonRequest = om.writeValueAsString(addressInfoDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/1/personal-address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PMAPIResponseMessage.ADDRESS_CREATE_SUCCESS))
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    public void updateAddressTest() throws Exception {
        response.setMessage(PMAPIResponseMessage.ADDRESS_UPDATE_SUCCESS);
        response.setSuccess(true);
        addressInfoDTO.setPresentAddress("Gulshan");

        /** initialize service with expected response*/
        Mockito.when(addressService.update(addressInfoDTO, user.getId())).thenReturn(response);

        String jsonRequest = om.writeValueAsString(addressInfoDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/personal-address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PMAPIResponseMessage.ADDRESS_UPDATE_SUCCESS))
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    public void readAddressTest() throws Exception {
        PersonalAddressInfo addressEntity = PersonalAddressInfoDTO.getPersonalAddressInfoEntity(addressInfoDTO);
        response.setMessage(PMAPIResponseMessage.ADDRESS_RECORD_FOUND);
        response.setSuccess(true);
        response.setData(addressEntity);

        /** testing service for read: Controller to Service */
        Mockito.when(addressService.read(user.getId())).thenReturn(response);
        // [Checking if service is available]

        /** client to controller-- > just the request object */
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/users/1/personal-address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        /** Call controller using request object */
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PMAPIResponseMessage.ADDRESS_RECORD_FOUND))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(addressEntity));
    }
}
