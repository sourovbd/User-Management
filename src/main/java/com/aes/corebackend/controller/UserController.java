package com.aes.corebackend.controller;

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
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserService userService;

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
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto, @PathVariable long id) {
        User user = userDto.dtoToUser(userDto);
        boolean success = userService.update(user,id);
        if (success) {
            //emailSender.send(userDto.dtoToUser(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new UserCreationResponseDTO("user data updated"));
        }
        else
            return ResponseEntity.ok(new UserCreationResponseDTO("user update failed"));

    }
    @GetMapping("get/user/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable int id) {
        User user = userService.findById(id).orElse(null);
        return ResponseEntity.ok(new UserFinderResponseDTO("use fetch ok",user));
    }
}
