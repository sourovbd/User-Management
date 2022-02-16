package com.aes.corebackend.unittest.service.personnelmanagement;

import com.aes.corebackend.service.personnelmanagement.PersonalBasicInformationService;
import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.enumeration.Gender;
import com.aes.corebackend.repository.personnelmanagement.PersonalBasicInfoRepository;
import com.aes.corebackend.service.usermanagement.UserService;
import org.junit.jupiter.api.BeforeEach;
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
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonalBasicInfoServiceTest {

    @Autowired
    private PersonalBasicInformationService personalBasicInformationService;

    @MockBean
    private PersonalBasicInfoRepository personalBasicInfoRepository;

    @MockBean
    private UserService userService;

    private User user = new User();
    private PersonalBasicInfo personalBasicInfo = new PersonalBasicInfo();
    private PersonalBasicInfoDTO personalBasicInfoDTO = new PersonalBasicInfoDTO();
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    private void setup() throws ParseException {
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");

        personalBasicInfo.setId(1L);
        personalBasicInfo.setFirstName("jahangir");
        personalBasicInfo.setLastName("alam");
        personalBasicInfo.setDateOfBirth(formatter.parse("12-09-1989"));
        personalBasicInfo.setGender(Gender.MALE);

        personalBasicInfoDTO.setId(1L);
        personalBasicInfoDTO.setFirstName("jahangir");
        personalBasicInfoDTO.setLastName("alam");
        personalBasicInfoDTO.setDateOfBirth(formatter.parse("12-09-1989"));
        personalBasicInfoDTO.setGender(Gender.MALE);
    }

    @Test
    public void createPersonalBasicInformationTest() {
        expectedResponse.setResponse(BASIC_INFORMATION_CREATE_SUCCESS, TRUE, null, SUCCESS);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalBasicInfoRepository.save(personalBasicInfo)).thenReturn(personalBasicInfo);

        APIResponse actualResponse = personalBasicInformationService.create(personalBasicInfoDTO, user.getId());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void updatePersonalBasicInformationTest() {
        personalBasicInfoDTO.setFirstName("mdjahangir");
        expectedResponse.setResponse(BASIC_INFORMATION_UPDATE_SUCCESS, TRUE, null, SUCCESS);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalBasicInfoRepository.findPersonalBasicInfoByUserId(1L)).thenReturn(personalBasicInfo);

        APIResponse actualResponse = personalBasicInformationService.update(personalBasicInfoDTO, user.getId());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void readPersonalBasicInformationTest() {
        expectedResponse.setResponse(BASIC_INFORMATION_RECORD_FOUND, TRUE, personalBasicInfoDTO, SUCCESS);

        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalBasicInfoRepository.findPersonalBasicInfoByUserId(1L)).thenReturn(personalBasicInfo);

        APIResponse actualResponse = personalBasicInformationService.read(user.getId());
        assertEquals(expectedResponse, actualResponse);
    }
}
