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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> saveCredential(@RequestBody UserCredentialDTO userCredentialDTO) {

        return userCredentialService.save(userCredentialDTO.to(userCredentialDTO))?
                ResponseEntity.ok("Saved Successfully") :
                ResponseEntity.ok("Save Failed");
    }

    @PostMapping("/users/create")
    @PreAuthorize("hasRole('SYS_ADMIN')")
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
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto, @PathVariable long id) {
        return userService.update(userDto.dtoToUser(userDto),id)?
                ResponseEntity.ok(new UserCreationResponseDTO("user data updated")) :
                ResponseEntity.ok(new UserCreationResponseDTO("user update failed"));
    }
    
    @PostMapping("users/reset-password/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> updateCredential(@RequestBody UserCredentialDTO userCredentialDTO, @PathVariable Long id) {

        return userCredentialService.update(userCredentialDTO.to(userCredentialDTO), id) ?
                ResponseEntity.ok("Updated Successfully") :
                ResponseEntity.ok("Update Failed");
    }

    @PostMapping("users/verify-credential")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> verifyCredential(@RequestBody UserCredentialDTO userCredentialDTO) {
        boolean verified = userCredentialService.verifyPassword(userCredentialDTO);
        if (verified) {
            return ResponseEntity.ok("Valid Password");
        }
        else {
            return ResponseEntity.ok("Invalid Password");
        }
    }
    
    @GetMapping("get/user/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> getUserDetails(@PathVariable int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserFinderResponseDTO("user fetch ok",user));
    }
    @GetMapping("get/users")
    public ResponseEntity<?> getAllUsers() {
        List<User>  users = userService.findAllUsers();
        return ResponseEntity.ok(new UsersFetchResponseDTO("All user fetch ok",users));
    }

    @PostMapping("users/forgot-password")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        /** email validation */
        boolean done = userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress());
        if (done) {
            return ResponseEntity.ok("A new password is sent to your email.");
        }
        else {
            return ResponseEntity.ok("Pleas try again.");
        }
    }
}
