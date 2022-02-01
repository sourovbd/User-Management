package com.aes.corebackend.controller;

import com.aes.corebackend.dto.*;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.service.UserCredentialService;
import com.aes.corebackend.service.EmailSender;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {

        UserCredential userCredential = userCredentialService.getByEmployeeId(userDto.getEmployeeId());
        User user = userDto.dtoToEntity(userDto);
        user.setUserCredential(userCredential);

        if (Objects.nonNull(userService.save(user))) {
            emailSender.send(userDto.dtoToEntity(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new UserCreationResponseDTO("user created"));
        }
        else
            return ResponseEntity.ok(new UserCreationResponseDTO("user creation failed"));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDto, @PathVariable long id) {

        return ResponseEntity.ok(userService.update(userDto.dtoToEntity(userDto),id));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getUserDetails(@PathVariable int id) {

        return ResponseEntity.ok(new UserFinderResponseDTO("user fetch ok", userService.findById(id)));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getAllUsers() {

        return ResponseEntity.ok(new UsersFetchResponseDTO("All user fetch ok", userService.findAllUsers()));
    }

}
