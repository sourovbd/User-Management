package com.sv.corebackend.controller.usermanagement;

import com.sv.corebackend.dto.usermanagement.ForgotPasswordDTO;
import com.sv.corebackend.dto.usermanagement.UserCredentialDTO;
import com.sv.corebackend.service.usermanagement.UserCredentialService;
import com.sv.corebackend.util.response.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class  UserCredentialController {

    private final UserCredentialService userCredentialService;

    private static final Logger logger = LoggerFactory.getLogger(UserCredentialController.class);

    @PostMapping("/users-credential")
    public ResponseEntity<?> saveCredential(@RequestBody @Valid UserCredentialDTO userCredentialDTO) {
        APIResponse apiResponse = userCredentialService.update(userCredentialDTO.to(userCredentialDTO));
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);

    }

    @PostMapping("/users/reset-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updateCredential(@Valid @RequestBody UserCredentialDTO userCredentialDTO) {
        APIResponse apiResponse = userCredentialService.update(userCredentialDTO.to(userCredentialDTO));
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    /** During login */
    @PostMapping("/users/verify-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> verifyCredential(@RequestBody UserCredentialDTO userCredentialDTO) {
        APIResponse apiResponse = userCredentialService.verifyPassword(userCredentialDTO);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PostMapping("/users/forgot-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        APIResponse apiResponse = userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress());
        return  apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

}