package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalAddressInfoRepository;
import com.aes.corebackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonalAddressServiceTest {

    @Autowired
    private PersonalAddressService personalAddressService;

    @MockBean
    private PersonalAddressInfoRepository personalAddressInfoRepository;

    @MockBean
    private UserService userService;

    private User user = new User();
    private PersonalAddressInfo personalAddressInfo = new PersonalAddressInfo();
    PersonalAddressInfoDTO personalAddressInfoDTO = new PersonalAddressInfoDTO();
    APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    public void setup() {
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");

        personalAddressInfoDTO.setId(1L);
        personalAddressInfoDTO.setPresentAddress("Dhaka");
        personalAddressInfoDTO.setPermanentAddress("Kishoreganj");

        personalAddressInfo.setId(1L);
        personalAddressInfo.setPresentAddress("Dhaka");
        personalAddressInfo.setPermanentAddress("Kishoreganj");
    }

    @Test
    public void createPersonalAddressTest() {
        expectedResponse.setResponse(ADDRESS_CREATE_SUCCESS, TRUE, null);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAddressInfoRepository.save(personalAddressInfo)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.create(personalAddressInfoDTO, user.getId());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void updatePersonalAddressTest() {
        expectedResponse.setResponse(ADDRESS_UPDATE_SUCCESS, TRUE, null);
        personalAddressInfo.setPermanentAddress("CTG");
        personalAddressInfoDTO.setPermanentAddress("CTG");

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAddressInfoRepository.findPersonalAddressInfoByUserId(1L)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.update(personalAddressInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void readPersonalAddressTest() {
        expectedResponse.setResponse(ADDRESS_RECORD_FOUND, TRUE, personalAddressInfoDTO);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAddressInfoRepository.findPersonalAddressInfoByUserId(1L)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }
}
