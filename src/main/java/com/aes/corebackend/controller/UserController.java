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

import java.util.Optional;

@RestController
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UserService userService;
    @PostMapping("/user/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {
        boolean success = userService.save(userDto.dtoToUser(userDto));
        if (success) {
            emailSender.send(userDto.dtoToUser(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new UserCreationResponseDTO("user created", success));
        }
        else
            return ResponseEntity.ok(new UserCreationResponseDTO("user creation failed", success));

    }
    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto) {
        User user = userDto.dtoToUser(userDto);
        boolean success = userService.update(user);
        if (success) {
            //emailSender.send(userDto.dtoToUser(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new UserCreationResponseDTO("user data updated", success));
        }
        else
            return ResponseEntity.ok(new UserCreationResponseDTO("user update failed", success));

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable int id) {
        Optional<User> user = userService.findById(id);
        return ResponseEntity.ok(new UserFinderResponseDTO("use fetch ok",user));
    }
}
