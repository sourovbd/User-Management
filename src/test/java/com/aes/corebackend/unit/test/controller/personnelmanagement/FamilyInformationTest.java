package com.aes.corebackend.unit.test.controller.personnelmanagement;

import com.aes.corebackend.controller.personnelmanagement.FamilyInformationController;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.service.personnelmanagement.FamilyInformationService;
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

import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FamilyInformationTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private FamilyInformationController controller;

    @Mock
    private FamilyInformationService service;


    ObjectMapper om = new ObjectMapper();
    User user = new User();
    PersonalFamilyInfoDTO familyDTO = new PersonalFamilyInfoDTO();

    @BeforeEach
    public void setup() {   //Intialize mocks with openMocks, creating a mock controller
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        user.setId(1L);
        user.setDesignation("MD");
        user.setDepartment("Company");
        user.setEmailAddress("hossainfurkaan@gmail.com");
        user.setBusinessUnit("Anwar Enterprise Systems Ltd.");
        user.setEmployeeId("0001");

        familyDTO.setSpouseName("Arham Amani Ahmed");
        familyDTO.setMothersName("Haseena Parveen Manwar");
        familyDTO.setFathersName("Manwar Hossain");
        familyDTO.setMaritalStatus("Married");
    }

    @Test
    public void createFamilyInformationTest() throws Exception {

        APIResponse responseDTO = new APIResponse("Create Family Info Success", true, null, SUCCESS);

        Mockito.when(service.create(familyDTO, user.getId())).thenReturn(responseDTO);// initialize service with expected response

        String jsonRequest = om.writeValueAsString(familyDTO);  // payload
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/users/1/family-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Create Family Info Success"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void updateAttributesTest() throws Exception {

        APIResponse responseDTO = new APIResponse("Update Family Info Success", true, null, SUCCESS);
        familyDTO.setMothersName("Neela Manwar");
        Mockito.when(service.update(familyDTO, user.getId())).thenReturn(responseDTO);// initialize service with expected response

        String jsonRequest = om.writeValueAsString(familyDTO);  // payload
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/users/1/family-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Update Family Info Success"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void getAttributesTest() throws Exception {
        PersonalFamilyInfo family = PersonalFamilyInfoDTO.getPersonalFamilyEntity(familyDTO);
        APIResponse responseDTO = new APIResponse("Family Information found",
                true,
                family, SUCCESS);//Expected return

        Mockito.when(service.read(user.getId())).thenReturn(responseDTO);// testing service for read: Controller to Service
        // [Checking if service is available]

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders  //client to controller-- > just the request object
                .get("/users/1/family-information")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)    //Call controller using request object
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Family Information found"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(family));
    }

}
