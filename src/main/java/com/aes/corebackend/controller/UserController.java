package com.aes.corebackend.controller;

import com.aes.corebackend.dto.UserCreationResponseDTO;
import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.service.EmailSender;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {
        boolean success = userService.save(userDto.dtoToUser(userDto));
        if (success) {
            emailSender.send(userDto.dtoToUser(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new UserCreationResponseDTO("user created", success));
        }
        else
            return ResponseEntity.ok(new UserCreationResponseDTO("user creation failed", success));

    }


}
