package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalEducationRepository;
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

import java.util.ArrayList;

import static com.aes.corebackend.util.response.AjaxResponseStatus.ERROR;
import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonalEducationServiceTest {

    @Autowired
    private PersonalEducationService personalEducationService;

    @MockBean
    private PersonalEducationRepository personalEducationRepository;

    @MockBean
    private UserService userService;

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts", null, null, null, null);
    private PersonalEducationInfo personalEducationInfoSSC = new PersonalEducationInfo(1L, "SSC", "ABC High School", 5.00f, 4.90f, "2015", user);
    private PersonalEducationInfo personalEducationInfoHSC = new PersonalEducationInfo(2L, "HSC", "ABC College", 5.00f, 4.90f, "2017", user);
    private PersonalEducationInfo personalEducationInfoBSC = new PersonalEducationInfo(3L, "BSC", "ABC University", 4.00f, 3.90f, "2021", user);
    private PersonalEducationDTO personalEducationDTO = new PersonalEducationDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    public void setup() {
        personalEducationDTO.setDegreeName("SSC");
        personalEducationDTO.setInstitutionName("ABC High School");
        personalEducationDTO.setGpaScale(5.00f);
        personalEducationDTO.setCgpa(4.90f);
        personalEducationDTO.setPassingYear("2015");
    }

    @Test
    @DisplayName("This is create - success")
    public void createTestSuccess() {

        expectedResponse.setResponse(EDUCATION_CREATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalEducationRepository.save(PersonalEducationDTO.getPersonalEducationEntity(personalEducationDTO)))
                .thenReturn(personalEducationInfoSSC);

        APIResponse actualResponse = personalEducationService.create(personalEducationDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is create - failed")
    public void createTestFailed() {

        expectedResponse.setResponse(EDUCATION_CREATE_FAIL, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalEducationRepository.save(Mockito.any(PersonalEducationInfo.class)))
                .thenThrow(new RuntimeException());

        APIResponse actualResponse = personalEducationService.create(personalEducationDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is update - success")
    public void updateTestSuccess() {

        expectedResponse.setResponse(EDUCATION_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalEducationRepository.findPersonalEducationInfoByIdAndUserId(1L, 1L))
                .thenReturn(personalEducationInfoSSC);

        APIResponse actualResponse = personalEducationService.update(personalEducationDTO, 1L, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is update - failed")
    public void updateTestFailed() {

        expectedResponse.setResponse(EDUCATION_UPDATE_FAIL, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalEducationRepository.findPersonalEducationInfoByIdAndUserId(1L, 1L))
                .thenReturn(personalEducationInfoSSC);
        Mockito.when(personalEducationRepository.save(Mockito.any(PersonalEducationInfo.class)))
                .thenThrow(new RuntimeException());

        APIResponse actualResponse = personalEducationService.update(personalEducationDTO, 1L, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is read - all education records for a user - success")
    public void readEducationsTestSuccess() {

        ArrayList<PersonalEducationInfo> educationInfoList = new ArrayList<>();
        educationInfoList.add(personalEducationInfoSSC);
        educationInfoList.add(personalEducationInfoHSC);
        educationInfoList.add(personalEducationInfoBSC);

        ArrayList<PersonalEducationDTO> educationDTOS = new ArrayList<>();
        educationDTOS.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoSSC));
        educationDTOS.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoHSC));
        educationDTOS.add(PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoBSC));

        expectedResponse.setResponse(EDUCATION_RECORDS_FOUND, TRUE, educationDTOS, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalEducationRepository.findPersonalEducationInfoByUserId(1L))
                .thenReturn(educationInfoList);

        APIResponse actualResponse = personalEducationService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is read - all education records for a user - failed")
    public void readEducationsTestFailed() {

        expectedResponse.setResponse(EDUCATION_RECORD_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalEducationRepository.findPersonalEducationInfoByUserId(1L))
                .thenReturn(new ArrayList<>());

        APIResponse actualResponse = personalEducationService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is read - a single record - success")
    public void readTestSuccess() {

        expectedResponse.setResponse(EDUCATION_RECORD_FOUND, TRUE, PersonalEducationDTO.getPersonalEducationDTO(personalEducationInfoSSC), SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalEducationRepository.findPersonalEducationInfoByIdAndUserId(1L, 1L))
                .thenReturn(personalEducationInfoSSC);

        APIResponse actualResponse = personalEducationService.read(1L, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is read - a single record - failed")
    public void readTestFailed() {

        expectedResponse.setResponse(EDUCATION_RECORD_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalEducationRepository.findPersonalEducationInfoByIdAndUserId(4L, 1L))
                .thenReturn(null);

        APIResponse actualResponse = personalEducationService.read(1L, 4L);
        assertEquals(expectedResponse, actualResponse);
    }
}
