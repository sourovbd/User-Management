package com.aes.corebackend.controller;

import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.service.UserCredentialService;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

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
}
