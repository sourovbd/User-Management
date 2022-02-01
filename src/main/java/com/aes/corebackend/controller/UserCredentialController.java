package com.aes.corebackend.controller;

import com.aes.corebackend.dto.ForgotPasswordDTO;
import com.aes.corebackend.dto.UserCreationResponseDTO;
import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.dto.UserCredentialResponseDTO;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class UserCredentialController {

    @Autowired
    private UserCredentialService userCredentialService;

    @PostMapping("/users-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> saveCredential(@RequestBody @Valid UserCredentialDTO userCredentialDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.ok(new UserCreationResponseDTO(result.getFieldError().getDefaultMessage()));
        }

        return userCredentialService.save(userCredentialDTO.to(userCredentialDTO))?
                ResponseEntity.ok(new UserCredentialResponseDTO("Saved Successfully")) :
                ResponseEntity.ok(new UserCredentialResponseDTO("Save Failed"));
    }

    @PostMapping("users/reset-password/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updateCredential(@Valid @RequestBody UserCredentialDTO userCredentialDTO, @PathVariable Long id) {

        return userCredentialService.update(userCredentialDTO.to(userCredentialDTO), id) ?
                ResponseEntity.ok(new UserCredentialResponseDTO("Updated Successfully")) :
                ResponseEntity.ok(new UserCredentialResponseDTO("Update Failed"));
    }

    @PostMapping("users/verify-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> verifyCredential(@RequestBody UserCredentialDTO userCredentialDTO) {

        return userCredentialService.verifyPassword(userCredentialDTO) ?
                ResponseEntity.ok(new UserCredentialResponseDTO("Valid Password")) :
                ResponseEntity.ok(new UserCredentialResponseDTO("Invalid Password"));
    }

    @PostMapping("/users/forgot-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.ok(new UserCreationResponseDTO(result.getFieldError().getDefaultMessage()));
        }

        return userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress()) ?
                ResponseEntity.ok(new UserCredentialResponseDTO("A new password is sent to your email.")) :
                ResponseEntity.ok(new UserCredentialResponseDTO("Please try again."));
    }

    @GetMapping("users/{employeeId}/fetch-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> forgotPassword(@PathVariable String employeeId) {

        UserCredential userCredential = userCredentialService.getByEmployeeId(employeeId);

        return Objects.isNull(userCredential) ?
                ResponseEntity.ok(new UserCredentialResponseDTO("Not Found")) :
                ResponseEntity.ok(new UserCredentialResponseDTO("Found", userCredential));
    }
}
