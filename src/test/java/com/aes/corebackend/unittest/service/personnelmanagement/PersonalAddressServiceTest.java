package com.aes.corebackend.unittest.service.personnelmanagement;

import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalAddressInfoRepository;
import com.aes.corebackend.service.usermanagement.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.aes.corebackend.util.response.APIResponseStatus.*;

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
    private PersonalAddressInfoDTO personalAddressInfoDTO = new PersonalAddressInfoDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

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
    @DisplayName("create address record - success")
    public void createPersonalAddressSuccessTest() {
        expectedResponse.setResponse(ADDRESS_CREATE_SUCCESS, TRUE, null, SUCCESS);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAddressInfoRepository.save(personalAddressInfo)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.create(personalAddressInfoDTO, user.getId());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("create address record - failure")
    public void createPersonalAddressFailureUserNotFoundTest() {
        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(null);
        Mockito.when(personalAddressInfoRepository.save(personalAddressInfo)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.create(personalAddressInfoDTO, user.getId());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("update address record - success")
    public void updatePersonalAddressSuccessTest() {
        expectedResponse.setResponse(ADDRESS_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        personalAddressInfo.setPermanentAddress("CTG");
        personalAddressInfoDTO.setPermanentAddress("CTG");

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAddressInfoRepository.findPersonalAddressInfoByUserId(1L)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.update(personalAddressInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("update address record - failure")
    public void updatePersonalAddressFailureUserNotFoundTest() {
        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        personalAddressInfo.setPermanentAddress("CTG");
        personalAddressInfoDTO.setPermanentAddress("CTG");

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(null);
        Mockito.when(personalAddressInfoRepository.findPersonalAddressInfoByUserId(1L)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.update(personalAddressInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("read address record - success")
    public void readPersonalAddressSuccessTest() {
        expectedResponse.setResponse(ADDRESS_RECORD_FOUND, TRUE, personalAddressInfoDTO, SUCCESS);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAddressInfoRepository.findPersonalAddressInfoByUserId(1L)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("read address record - failure")
    public void readPersonalAddressFailureUserNotFoundTest() {
        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(null);
        Mockito.when(personalAddressInfoRepository.findPersonalAddressInfoByUserId(1L)).thenReturn(personalAddressInfo);

        APIResponse actualResponse = personalAddressService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }
}
