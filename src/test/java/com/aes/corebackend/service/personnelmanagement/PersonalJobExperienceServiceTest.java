package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import com.aes.corebackend.repository.personnelmanagement.PersonalJobExperienceRepository;
import com.aes.corebackend.service.usermanagement.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.aes.corebackend.util.response.APIResponseStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonalJobExperienceServiceTest {

    @Autowired
    private PersonalJobExperienceService personalJobExperienceService;

    @MockBean
    PersonalJobExperienceRepository personalJobExperienceRepository;

    @MockBean
    private UserService userService;

    private User user = new User();
    private PersonalJobExperience personalJobExperience1 = new PersonalJobExperience();
    private PersonalJobExperience personalJobExperience2 = new PersonalJobExperience();
    private PersonalJobExperienceDTO personalJobExperienceDTO1 = new PersonalJobExperienceDTO();
    private PersonalJobExperienceDTO personalJobExperienceDTO2 = new PersonalJobExperienceDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();
    private final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    private void setup() throws ParseException {
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");

        personalJobExperience1.setId(1L);
        personalJobExperience1.setEmployerName("REVE");
        personalJobExperience1.setDesignation("SDE");
        personalJobExperience1.setStartDate(formatter.parse("12-03-2017"));
        personalJobExperience1.setEndDate(formatter.parse("12-12-2020"));
        personalJobExperience1.setResponsibilities("development");

        personalJobExperience2.setId(2L);
        personalJobExperience2.setEmployerName("Nascenia");
        personalJobExperience2.setDesignation("JrDeveloper");
        personalJobExperience2.setStartDate(formatter.parse("01-10-2015"));
        personalJobExperience2.setEndDate(formatter.parse("12-02-2017"));
        personalJobExperience2.setResponsibilities("development");

        personalJobExperienceDTO1.setId(1L);
        personalJobExperienceDTO1.setEmployerName("REVE");
        personalJobExperienceDTO1.setDesignation("SDE");
        personalJobExperienceDTO1.setStartDate(formatter.parse("12-03-2017"));
        personalJobExperienceDTO1.setEndDate(formatter.parse("12-12-2020"));
        personalJobExperienceDTO1.setResponsibilities("development");

        personalJobExperienceDTO2.setId(2L);
        personalJobExperienceDTO2.setEmployerName("Nascenia");
        personalJobExperienceDTO2.setDesignation("JrDeveloper");
        personalJobExperienceDTO2.setStartDate(formatter.parse("01-10-2015"));
        personalJobExperienceDTO2.setEndDate(formatter.parse("12-02-2017"));
        personalJobExperienceDTO2.setResponsibilities("development");
    }

    @Test
    @DisplayName("create job experience record - success")
    public void createPersonalJobExperienceSuccessTest() {
        expectedResponse.setResponse(JOB_EXPERIENCE_CREATE_SUCCESS, TRUE, null, SUCCESS);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalJobExperienceRepository.save(personalJobExperience1)).thenReturn(personalJobExperience1);

        APIResponse actualResponse = personalJobExperienceService.create(personalJobExperienceDTO1, 1L);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("create job experience record - failure")
    public void createPersonalJobExperienceFailureUserNotFoundTest() {
        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(null);
        Mockito.when(personalJobExperienceRepository.save(personalJobExperience1)).thenReturn(personalJobExperience1);

        APIResponse actualResponse = personalJobExperienceService.create(personalJobExperienceDTO1, 1L);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("update job experience record - success")
    public void updatePersonalJobExperienceSuccessTest() {
        expectedResponse.setResponse(JOB_EXPERIENCE_UPDATE_SUCCESS, TRUE, null, SUCCESS);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(1L, 1L)).thenReturn(personalJobExperience1);

        APIResponse actualResponse = personalJobExperienceService.update(personalJobExperienceDTO1, 1L, 1L);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("update job experience record - failure")
    public void updatePersonalJobExperienceFailureUserNotFoundTest() {
        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(null);
        Mockito.when(personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(1L, 1L)).thenReturn(personalJobExperience1);

        APIResponse actualResponse = personalJobExperienceService.update(personalJobExperienceDTO1, 1L, 1L);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("read single job experience record - success")
    public void readSingleJobExperienceSuccessTest() {

        expectedResponse.setResponse(JOB_EXPERIENCE_RECORD_FOUND, TRUE, personalJobExperienceDTO1, SUCCESS);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(1L, 1L)).thenReturn(personalJobExperience1);

        APIResponse actualResponse = personalJobExperienceService.read(1L, 1L);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("read single job experience record - failure")
    public void readSingleJobExperienceFailureUserNotFoundTest() {

        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(null);
        Mockito.when(personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(1L, 1L)).thenReturn(personalJobExperience1);

        APIResponse actualResponse = personalJobExperienceService.read(1L, 1L);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("read multiple job experience record - success")
    public void readMultipleJobExperienceSuccessTest() {

        ArrayList<PersonalJobExperienceDTO> jobExperienceDTOList = new ArrayList<>();
        jobExperienceDTOList.add(personalJobExperienceDTO1);
        jobExperienceDTOList.add(personalJobExperienceDTO2);
        expectedResponse.setResponse(JOB_EXPERIENCE_RECORD_FOUND, TRUE, jobExperienceDTOList, SUCCESS);

        ArrayList<PersonalJobExperience> jobExperienceEntityList = new ArrayList<>();
        jobExperienceEntityList.add(personalJobExperience1);
        jobExperienceEntityList.add(personalJobExperience2);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalJobExperienceRepository.findPersonalJobExperiencesByUserId(1L)).thenReturn(jobExperienceEntityList);

        APIResponse actualResponse = personalJobExperienceService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("read multiple job experience record - failure")
    public void readMultipleJobExperienceFailureUserNotFoundTest() {

        ArrayList<PersonalJobExperienceDTO> jobExperienceDTOList = new ArrayList<>();
        jobExperienceDTOList.add(personalJobExperienceDTO1);
        jobExperienceDTOList.add(personalJobExperienceDTO2);
        expectedResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);

        ArrayList<PersonalJobExperience> jobExperienceEntityList = new ArrayList<>();
        jobExperienceEntityList.add(personalJobExperience1);
        jobExperienceEntityList.add(personalJobExperience2);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(null);
        Mockito.when(personalJobExperienceRepository.findPersonalJobExperiencesByUserId(1L)).thenReturn(jobExperienceEntityList);

        APIResponse actualResponse = personalJobExperienceService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }
}
