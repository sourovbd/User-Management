package com.aes.corebackend.unit.test.service;

import com.aes.corebackend.dto.usermanagement.UserCredentialDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.usermanagement.UserCredential;
import com.aes.corebackend.exception.ResourceNotFoundException;
import com.aes.corebackend.repository.usermanagement.UserCredentialRepository;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.aes.corebackend.service.usermanagement.EmailSender;
import com.aes.corebackend.service.usermanagement.UserCredentialService;
import com.aes.corebackend.util.response.APIResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.aes.corebackend.util.response.APIResponse.getApiResponse;
import static com.aes.corebackend.util.response.APIResponseStatus.ERROR;
import static com.aes.corebackend.util.response.UMAPIResponseMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserCredentialServiceTest {

    @Autowired
    private UserCredentialService userCredentialService;

    @MockBean
    private UserCredentialRepository userCredentialRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailSender emailSender;

    private APIResponse expectedResponse = getApiResponse();

    private UserCredential mockUserCredential = new UserCredential(1, "012580", "123@5Aa", true, "EMPLOYEE");

    @Test
    @DisplayName("Test get employee by employee id - Success")
    public void getEmployeeTest() {
        Mockito.when(userCredentialRepository.findByEmployeeId(mockUserCredential.getEmployeeId())).thenReturn(Optional.of(mockUserCredential));

        UserCredential returnedUserCredentialFromService = userCredentialService.getEmployee("012580");
        assertEquals(returnedUserCredentialFromService,mockUserCredential);
    }
    @Test
    public void updateUserCredentialTest() {
        APIResponse responseDTO = getApiResponse();
        Mockito.when(userCredentialRepository.save(mockUserCredential)).thenReturn(mockUserCredential);
        Mockito.when(userCredentialRepository.findByEmployeeId("012580")).thenReturn(Optional.ofNullable(mockUserCredential));
        responseDTO.setResponse(USER_CREDENTIAL_UPDATED_SUCCESSFULLY, TRUE, mockUserCredential, SUCCESS);
        APIResponse returnedResponse = userCredentialService.update(mockUserCredential);
        assertEquals(returnedResponse.getMessage(),responseDTO.getMessage());
        assertEquals(returnedResponse.isSuccess(),responseDTO.isSuccess());
        assertEquals(returnedResponse.getData(),responseDTO.getData());
        assertEquals(returnedResponse.getStatus(),responseDTO.getStatus());
    }

    @Test
    public void generateAndSendTempPassTest() {
        UserCredential userCredential = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
        User user = new User();
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("101");
        user.setRoles("EMPLOYEE");
        user.setUserCredential(userCredential);
        Mockito.when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(Optional.of(user));
        Mockito.when(userCredentialRepository.save(userCredential)).thenReturn(userCredential);
        APIResponse returnedResponse = userCredentialService.generateAndSendTempPass(user.getEmailAddress());
        APIResponse responseDTO = getApiResponse();
        responseDTO.setResponse(NEW_PASSWORD_SENT, TRUE, user, SUCCESS);
        assertEquals(returnedResponse.getMessage(),responseDTO.getMessage());
        assertEquals(returnedResponse.isSuccess(),responseDTO.isSuccess());
        assertEquals(returnedResponse.getData(),responseDTO.getData());
        assertEquals(returnedResponse.getStatus(),responseDTO.getStatus());
    }

}
