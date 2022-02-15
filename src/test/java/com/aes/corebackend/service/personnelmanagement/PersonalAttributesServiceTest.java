package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.repository.personnelmanagement.PersonalAttributesRepository;
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
public class PersonalAttributesServiceTest {

    @Autowired
    private PersonalAttributesService personalAttributesService;

    @MockBean
    private PersonalAttributesRepository personalAttributesRepository;

    @MockBean
    private UserService userService;

    private User user = new User(1L,"abc@gmail.com","agm","012580","a1polymar","accounts", null, null, null, null);
    private PersonalAttributes personalAttributes = new PersonalAttributes();
    private PersonalAttributesDTO personalAttributesDTO = new PersonalAttributesDTO();
    private APIResponse expectedResponse = APIResponse.getApiResponse();

    @BeforeEach
    public void setup() {
        personalAttributes.setId(1L);
        personalAttributes.setBirthPlace("BD");
        personalAttributes.setBloodGroup("O+");
        personalAttributes.setReligion("Islam");
        personalAttributes.setNationality("Bangladeshi");
        personalAttributes.setUser(user);

        personalAttributesDTO.setBirthPlace("BD");
        personalAttributesDTO.setBloodGroup("O+");
        personalAttributesDTO.setReligion("Islam");
        personalAttributesDTO.setNationality("Bangladeshi");
    }

    @Test
    @DisplayName("This is create - success")
    public void createSuccessTest() {

        expectedResponse.setResponse(ATTRIBUTES_CREATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAttributesRepository.save(PersonalAttributesDTO.getPersonalAttributesEntity(personalAttributesDTO)))
                .thenReturn(personalAttributes);

        APIResponse actualResponse = personalAttributesService.create(personalAttributesDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is create - failed")
    public void createFailedTest() {

        expectedResponse.setResponse(ATTRIBUTES_CREATE_FAIL, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAttributesRepository.save(Mockito.any(PersonalAttributes.class)))
                .thenThrow(new RuntimeException());

        APIResponse actualResponse = personalAttributesService.create(personalAttributesDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is update - success")
    public void updateSuccessTest() {

        expectedResponse.setResponse(ATTRIBUTES_UPDATE_SUCCESS, TRUE, null, SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAttributesRepository.findPersonalAttributesByUserId(1L))
                .thenReturn(personalAttributes);

        APIResponse actualResponse = personalAttributesService.update(personalAttributesDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is update - failed")
    public void updateFailedTest() {

        expectedResponse.setResponse(ATTRIBUTES_UPDATE_FAIL, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAttributesRepository.findPersonalAttributesByUserId(1L))
                .thenReturn(personalAttributes);
        Mockito.when(personalAttributesRepository.save(Mockito.any(PersonalAttributes.class)))
                .thenThrow(new RuntimeException());

        APIResponse actualResponse = personalAttributesService.update(personalAttributesDTO, 1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is read - success")
    public void readSuccessTest() {

        expectedResponse.setResponse(ATTRIBUTES_RECORD_FOUND, TRUE, PersonalAttributesDTO.getPersonalAttributesDTO(personalAttributes), SUCCESS);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAttributesRepository.findPersonalAttributesByUserId(1L))
                .thenReturn(personalAttributes);

        APIResponse actualResponse = personalAttributesService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("This is read - failed")
    public void readFailedTest() {

        expectedResponse.setResponse(ATTRIBUTES_RECORD_NOT_FOUND, FALSE, null, ERROR);
        Mockito.when(userService.getUserByUserId(1L)).thenReturn(user);
        Mockito.when(personalAttributesRepository.findPersonalAttributesByUserId(1L))
                .thenReturn(null);

        APIResponse actualResponse = personalAttributesService.read(1L);
        assertEquals(expectedResponse, actualResponse);
    }
}
