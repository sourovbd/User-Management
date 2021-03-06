package com.sv.corebackend.unittest.service;

import com.sv.corebackend.entity.User;
import com.sv.corebackend.entity.UserCredential;
import com.sv.corebackend.repository.UserCredentialRepository;
import com.sv.corebackend.repository.UserRepository;
import com.sv.corebackend.service.usermanagement.EmailSender;
import com.sv.corebackend.service.usermanagement.UserCredentialService;
import com.sv.corebackend.util.response.APIResponse;
import com.sv.corebackend.util.response.APIResponseStatus;
import com.sv.corebackend.util.response.UMAPIResponseMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    private APIResponse expectedResponse = APIResponse.getApiResponse();

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
        APIResponse responseDTO = APIResponse.getApiResponse();
        Mockito.when(userCredentialRepository.save(mockUserCredential)).thenReturn(mockUserCredential);
        Mockito.when(userCredentialRepository.findByEmployeeId("012580")).thenReturn(Optional.ofNullable(mockUserCredential));
        responseDTO.setResponse(UMAPIResponseMessage.USER_CREDENTIAL_UPDATED_SUCCESSFULLY, UMAPIResponseMessage.TRUE, mockUserCredential, APIResponseStatus.SUCCESS);
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
        APIResponse responseDTO = APIResponse.getApiResponse();
        responseDTO.setResponse(UMAPIResponseMessage.NEW_PASSWORD_SENT, UMAPIResponseMessage.TRUE, user, APIResponseStatus.SUCCESS);
        assertEquals(returnedResponse.getMessage(),responseDTO.getMessage());
        assertEquals(returnedResponse.isSuccess(),responseDTO.isSuccess());
        assertEquals(returnedResponse.getData(),responseDTO.getData());
        assertEquals(returnedResponse.getStatus(),responseDTO.getStatus());
    }

}
