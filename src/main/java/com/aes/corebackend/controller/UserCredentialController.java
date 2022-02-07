package com.aes.corebackend.controller;

import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.ForgotPasswordDTO;
import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.aes.corebackend.util.response.AjaxResponse.prepareErrorResponse;
import static org.springframework.http.ResponseEntity.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserCredentialController {

    private final UserCredentialService userCredentialService;

    private static final Logger logger = LoggerFactory.getLogger(UserCredentialController.class);

    @PostMapping("/users-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> saveCredential(@RequestBody @Valid UserCredentialDTO userCredentialDTO, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = userCredentialService.save(userCredentialDTO.to(userCredentialDTO));
        return apiResponse.isSuccess() ? ok(apiResponse.getData()) : badRequest().body(apiResponse.getData());

    }

    @PostMapping("/users/reset-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updateCredential(@Valid @RequestBody UserCredentialDTO userCredentialDTO, BindingResult result) {

        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = userCredentialService.update(userCredentialDTO.to(userCredentialDTO));
        return apiResponse.isSuccess() ? ok(apiResponse.getData()) : badRequest().body(apiResponse.getData());
    }

    /** During login */
    @PostMapping("/users/verify-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> verifyCredential(@RequestBody UserCredentialDTO userCredentialDTO) {
        APIResponse apiResponse = userCredentialService.verifyPassword(userCredentialDTO);
        return apiResponse.isSuccess() ? ok(apiResponse.getData()) : badRequest().body(apiResponse.getData());
    }

    @PostMapping("/users/forgot-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO, BindingResult result) {

        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress());
        return  apiResponse.isSuccess() ? ok(apiResponse.getData()) : badRequest().body(apiResponse.getData());
    }

}