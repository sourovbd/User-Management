package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalFamilyInfoRepository;
import com.aes.corebackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.aes.corebackend.util.response.AjaxResponseStatus.ERROR;
import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void initAll() {
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
    @DisplayName("This is create - no user")
    public void createTestNoUser() {

        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);

        APIResponse actualResponse = familyInformationService.create(personalFamilyInfoDTO, 2L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is create - success")
    public void createTestSuccess() {

        expectedResponse.setResponse(FAMILY_CREATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.save(PersonalFamilyInfoDTO.getPersonalFamilyEntity(personalFamilyInfoDTO)))
                .thenReturn(personalFamilyInfo);

        APIResponse actualResponse = familyInformationService.create(personalFamilyInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is create - failed")
    public void createTestFailed() {

        expectedResponse.setResponse(FAMILY_CREATE_FAIL, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.save(Mockito.any(PersonalFamilyInfo.class)))
                .thenThrow(new RuntimeException());

        APIResponse actualResponse = familyInformationService.create(personalFamilyInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is update - success")
    public void updateTestSuccess() {

        expectedResponse.setResponse(FAMILY_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(1L))
                .thenReturn(personalFamilyInfo);

        APIResponse actualResponse = familyInformationService.update(personalFamilyInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is update - failed")
    public void updateTestFailed() {

        expectedResponse.setResponse(FAMILY_UPDATE_FAIL, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(1L)).thenReturn(personalFamilyInfo);
        Mockito.when(personalFamilyInfoRepository.save(Mockito.any(PersonalFamilyInfo.class)))
                .thenThrow(new RuntimeException());

        APIResponse actualResponse = familyInformationService.update(personalFamilyInfoDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is read - success")
    public void readTestSuccess() {

        expectedResponse.setResponse(FAMILY_RECORD_FOUND, TRUE, PersonalFamilyInfoDTO.getPersonalFamilyDTO(personalFamilyInfo), SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(1L))
                .thenReturn(personalFamilyInfo);

        APIResponse actualResponse = familyInformationService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is read - failed")
    public void readTestFailed() {

        expectedResponse.setResponse(FAMILY_RECORD_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(1L))
                .thenReturn(null);

        APIResponse actualResponse = familyInformationService.read(1L);
        System.out.println(actualResponse.toString());
        assertEquals(expectedResponse, actualResponse);
    }
}
