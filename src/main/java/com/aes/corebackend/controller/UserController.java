package com.aes.corebackend.controller;

import com.aes.corebackend.dto.*;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.service.UserCredentialService;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.service.EmailSender;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialService userCredentialService;

    @PostMapping("users/save-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> saveCredential(@RequestBody @Valid UserCredentialDTO userCredentialDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.ok(new UserCreationResponseDTO(result.getFieldError().getDefaultMessage()));
        }

        return userCredentialService.save(userCredentialDTO.to(userCredentialDTO))?
                ResponseEntity.ok(new UserCredentialResponseDTO("Saved Successfully")) :
                ResponseEntity.ok(new UserCredentialResponseDTO("Save Failed"));
    }
        
    @PostMapping("/user/create")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {

        User user = userService.save(userDto.dtoToUser(userDto));
        if (Objects.nonNull(user)) {
            emailSender.send(userDto.dtoToUser(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new UserCreationResponseDTO("user created"));
        }
        else
            return ResponseEntity.ok(new UserCreationResponseDTO("user creation failed"));
    }
    
    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto) {
        return userService.update(userDto.dtoToUser(userDto))?
                ResponseEntity.ok(new UserCreationResponseDTO("user data updated")) :
                ResponseEntity.ok(new UserCreationResponseDTO("user update failed"));
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
    
    @GetMapping("get/user/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getUserDetails(@PathVariable int id) {
        User user = userService.findById(id).orElse(null);
        return ResponseEntity.ok(new UserFinderResponseDTO("use fetch ok",user));
    }

    @PostMapping("users/forgot-password")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.ok(new UserCreationResponseDTO(result.getFieldError().getDefaultMessage()));
        }

        return userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress()) ?
                ResponseEntity.ok(new UserCredentialResponseDTO("A new password is sent to your email.")) :
                ResponseEntity.ok(new UserCredentialResponseDTO("Please try again."));
    }

    @GetMapping ("users/{employeeId}/fetch-credential")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> forgotPassword(@PathVariable String employeeId) {
        UserCredential userCredential = userCredentialService.getByEmployeeId(employeeId);
        return userCredential==null ?
                ResponseEntity.ok(new UserCredentialResponseDTO("Not Found")) :
                ResponseEntity.ok(new UserCredentialResponseDTO("Found", userCredential));
    }
}
