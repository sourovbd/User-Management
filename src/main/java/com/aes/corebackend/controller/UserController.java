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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> saveCredential(@RequestBody UserCredentialDTO userCredentialDTO) {
        UserCredential userCredential = userCredentialDTO.to(userCredentialDTO);
        boolean saved = userCredentialService.save(userCredential);
        if (saved) {
            return ResponseEntity.ok("Saved Successfully");
        }
        else {
            return ResponseEntity.ok("Save Failed");
        }
    }
        
    @PostMapping("/user/create")
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
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto) {
        //User user = userDto.dtoToUser(userDto);
        //boolean success = userService.update(userDto.dtoToUser(userDto));

        return userService.update(userDto.dtoToUser(userDto))?
                ResponseEntity.ok(new UserCreationResponseDTO("user data updated")) :
                ResponseEntity.ok(new UserCreationResponseDTO("user update failed"));
        /*if (success) {
            //emailSender.send(userDto.dtoToUser(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new UserCreationResponseDTO("user data updated"));
        }
        else
            return ResponseEntity.ok(new UserCreationResponseDTO("user update failed"));*/
    }
    
    @PostMapping("users/reset-password/{id}")
    public ResponseEntity<?> updateCredential(@RequestBody UserCredentialDTO userCredentialDTO, @PathVariable Long id) {
        UserCredential userCredential = userCredentialDTO.to(userCredentialDTO);
        boolean updated = userCredentialService.update(userCredential, id);
        if (updated) {
            return ResponseEntity.ok("Updated Successfully");
        }
        else {
            return ResponseEntity.ok("Update Failed");
        }
    }

    @PostMapping("users/verify-credential")
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
    public ResponseEntity<?> getUserDetails(@PathVariable int id) {
        User user = userService.findById(id).orElse(null);
        return ResponseEntity.ok(new UserFinderResponseDTO("use fetch ok",user));
    }

    @PostMapping("users/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        //email validation
        boolean done = userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress());
        if (done) {
            return ResponseEntity.ok("A new password is sent to your email.");
        }
        else {
            return ResponseEntity.ok("Pleas try again.");
        }
    }
}
