package com.aes.corebackend.unittest.service.personnelmanagement;

import com.aes.corebackend.service.personnelmanagement.FamilyInformationService;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalFamilyInfoRepository;
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
public class FamilyInformationServiceTest {

    @Autowired
    private FamilyInformationService familyInformationService;

    @MockBean
    private PersonalFamilyInfoRepository personalFamilyInfoRepository;

    @MockBean
    private UserService userService;

    private User user = new User();
    private PersonalFamilyInfo personalFamilyInfo = new PersonalFamilyInfo();
    private PersonalFamilyInfoDTO personalFamilyInfoDTO = new PersonalFamilyInfoDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    public void setup() {
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("abc@gmail.com");
        user.setBusinessUnit("AIL");
        user.setEmployeeId("0101");

        personalFamilyInfo.setId(1L);
        personalFamilyInfo.setFathersName("Mr. John");
        personalFamilyInfo.setMothersName("Mrs. John");
        personalFamilyInfo.setMaritalStatus("Married");
        personalFamilyInfo.setSpouseName("");

        personalFamilyInfoDTO.setFathersName("Mr. John");
        personalFamilyInfoDTO.setMothersName("Mrs. John");
        personalFamilyInfoDTO.setMaritalStatus("Married");
        personalFamilyInfoDTO.setSpouseName("");
    }

    @Test
    @DisplayName("create family info - no user found")
    public void createNoUserTest() {

        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);

        APIResponse actualResponse = familyInformationService.create(personalFamilyInfoDTO, 2L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("create family info - success")
    public void createSuccessTest() {

        expectedResponse.setResponse(FAMILY_CREATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.save(PersonalFamilyInfoDTO.getPersonalFamilyEntity(personalFamilyInfoDTO)))
                .thenReturn(personalFamilyInfo);

        APIResponse actualResponse = familyInformationService.create(personalFamilyInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("create family info - failed")
    public void createFailedTest() {

        expectedResponse.setResponse(FAMILY_CREATE_FAIL, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.save(Mockito.any(PersonalFamilyInfo.class)))
                .thenThrow(new RuntimeException());

        APIResponse actualResponse = familyInformationService.create(personalFamilyInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("update family info - success")
    public void updateSuccessTest() {

        expectedResponse.setResponse(FAMILY_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(1L))
                .thenReturn(personalFamilyInfo);

        APIResponse actualResponse = familyInformationService.update(personalFamilyInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("update family info - failed")
    public void updateFailedTest() {

        expectedResponse.setResponse(FAMILY_UPDATE_FAIL, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(1L)).thenReturn(personalFamilyInfo);
        Mockito.when(personalFamilyInfoRepository.save(Mockito.any(PersonalFamilyInfo.class)))
                .thenThrow(new RuntimeException());

        APIResponse actualResponse = familyInformationService.update(personalFamilyInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("read family info - success")
    public void readSuccessTest() {

        expectedResponse.setResponse(FAMILY_RECORD_FOUND, TRUE, PersonalFamilyInfoDTO.getPersonalFamilyDTO(personalFamilyInfo), SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(1L))
                .thenReturn(personalFamilyInfo);

        APIResponse actualResponse = familyInformationService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("read family info - failed")
    public void readFailedTest() {

        expectedResponse.setResponse(FAMILY_RECORD_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(1L))
                .thenReturn(null);

        APIResponse actualResponse = familyInformationService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }
}
