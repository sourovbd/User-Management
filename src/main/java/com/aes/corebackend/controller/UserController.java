package com.aes.corebackend.controller;

import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.service.UserCredentialService;
import com.aes.corebackend.dto.UserCreationResponseDTO;
import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.dto.UserFinderResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.service.EmailSender;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialService userCredentialService;

    @PostMapping("users/save-credential")
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
    
    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto) {
        User user = userDto.dtoToUser(userDto);
        boolean success = userService.update(user);
        if (success) {
            //emailSender.send(userDto.dtoToUser(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new UserCreationResponseDTO("user data updated"));
        }
        else
            return ResponseEntity.ok(new UserCreationResponseDTO("user update failed"));
    }
    
    @PostMapping("users/update-credential")
    public ResponseEntity<?> updateCredential(@RequestBody UserCredentialDTO userCredentialDTO) {
        UserCredential userCredential = userCredentialDTO.to(userCredentialDTO);
        boolean updated = userCredentialService.update(userCredential);
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
}
