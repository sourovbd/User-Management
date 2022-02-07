package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
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
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("Personal address creation successful", true, null);

        /** initialize service with expected response*/
        Mockito.when(addressService.create(addressInfoDTO, user.getId())).thenReturn(responseDTO);

        String jsonRequest = om.writeValueAsString(addressInfoDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/1/personal-address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Personal address creation successful"))
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    public void updateAddressTest() throws Exception {
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("Personal address update successful", true, null);
        addressInfoDTO.setPresentAddress("Gulshan");

        /** initialize service with expected response*/
        Mockito.when(addressService.update(addressInfoDTO, user.getId())).thenReturn(responseDTO);

        String jsonRequest = om.writeValueAsString(addressInfoDTO);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/personal-address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Personal address update successful"))
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    public void readAddressTest() throws Exception {
        PersonalAddressInfo addressEntity = PersonalAddressInfoDTO.getPersonalAddressInfoEntity(addressInfoDTO);
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("Personal address record found",
                true,
                addressEntity);//Expected return

        /** testing service for read: Controller to Service */
        Mockito.when(addressService.read(user.getId())).thenReturn(responseDTO);
        // [Checking if service is available]

        /** client to controller-- > just the request object */
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/users/1/personal-address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        /** Call controller using request object */
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Personal address record found"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(addressEntity));
    }
}
