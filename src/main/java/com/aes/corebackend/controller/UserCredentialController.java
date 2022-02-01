package com.aes.corebackend.controller;

import com.aes.corebackend.dto.ForgotPasswordDTO;
import com.aes.corebackend.dto.ResponseDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserCredentialController {

    private final UserCredentialService userCredentialService;

    private static final Logger logger = LoggerFactory.getLogger(UserCredentialController.class);

    @PostMapping("/users-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> saveCredential(@RequestBody @Valid UserCredentialDTO userCredentialDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.ok(new ResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }

        return userCredentialService.save(userCredentialDTO.to(userCredentialDTO))?
                ResponseEntity.ok(new ResponseDTO("Saved Successfully", true, null)) :
                ResponseEntity.ok(new ResponseDTO("Save Failed", false, null));
    }

    @PostMapping("users/{employeeId}/reset-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updateCredential(@Valid @RequestBody UserCredentialDTO userCredentialDTO, BindingResult result, @PathVariable String employeeId) {
        if (result.hasErrors()) {
            return ResponseEntity.ok(new ResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }

        return userCredentialService.update(userCredentialDTO.to(userCredentialDTO), employeeId) ?
                ResponseEntity.ok(new ResponseDTO("Updated Successfully", true, null)) :
                ResponseEntity.ok(new ResponseDTO("Update Failed", false, null));
    }

    /** During login */
    @PostMapping("users/verify-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> verifyCredential(@RequestBody UserCredentialDTO userCredentialDTO) {

        return userCredentialService.verifyPassword(userCredentialDTO) ?
                ResponseEntity.ok(new ResponseDTO("Valid Password", true, null)) :
                ResponseEntity.ok(new ResponseDTO("Invalid Password", false, null));
    }

    @PostMapping("/users/forgot-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.ok(new ResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }

        return userCredentialService. generateAndSendTempPass(forgotPasswordDTO.getEmailAddress()) ?
                ResponseEntity.ok(new ResponseDTO("A new password is sent to your email.", true, null)) :
                ResponseEntity.ok(new ResponseDTO("Please try again.", false, null));
    }

}