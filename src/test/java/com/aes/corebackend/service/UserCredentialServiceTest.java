package com.aes.corebackend.service;

import com.aes.corebackend.entity.usermanagement.UserCredential;
import com.aes.corebackend.repository.usermanagement.UserCredentialRepository;
import com.aes.corebackend.service.usermanagement.UserCredentialService;
import com.aes.corebackend.util.response.APIResponse;
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
import static com.aes.corebackend.util.response.UMAPIResponseMessage.TRUE;
import static com.aes.corebackend.util.response.UMAPIResponseMessage.USER_CREDENTIAL_CREATED_SUCCESSFULLY;
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

    private APIResponse expectedResponse = getApiResponse();

    private UserCredential mockUserCredential = new UserCredential(1, "012580", "123@5Aa", true, "EMPLOYEE");

    @Test
    @DisplayName("Test getmployee by employee id - Success")
    public void testGetEmployee() {
        Mockito.when(userCredentialRepository.findByEmployeeId(mockUserCredential.getEmployeeId())).thenReturn(Optional.of(mockUserCredential));

        UserCredential returnedUserCredentialFromService = userCredentialService.getEmployee("012580");
        assertEquals(returnedUserCredentialFromService,mockUserCredential);
    }

    /*@Test
    @DisplayName("Test save Employee by employee id - Success")
    public void testSave() {
        testGetEmployee();
        expectedResponse.setResponse(USER_CREDENTIAL_CREATED_SUCCESSFULLY, TRUE, mockUserCredential, SUCCESS);
        Mockito.when(userCredentialRepository.save(mockUserCredential)).thenReturn((UserCredential)expectedResponse.getData());

        APIResponse actualResponse = userCredentialService.update(mockUserCredential);
        assertNotNull(actualResponse.getData(), "The saved user credential should not be null.");
        assertEquals(actualResponse, expectedResponse);
    }*/

}
