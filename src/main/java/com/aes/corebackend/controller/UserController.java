package com.aes.corebackend.controller;

import com.aes.corebackend.dto.*;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.service.UserCredentialService;
import com.aes.corebackend.service.EmailSender;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final EmailSender emailSender;

    private final UserService userService;

    private final UserCredentialService userCredentialService;

    /** static is used so that it only happens once */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> create(@RequestBody UserDTO userDto) {
        User user = userDto.dtoToEntity(userDto);
        if (Objects.nonNull(userService.create(user,userDto))) {
            emailSender.send(userDto.dtoToEntity(userDto).getEmailAddress(),"This is a test email");
            return ResponseEntity.ok(new ResponseDTO("user created",true,null));
        }
        else
            return ResponseEntity.ok(new ResponseDTO("user creation failed", true, null));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updateUser(@RequestBody @Valid  UserDTO userDto, @PathVariable long id) {

        return ResponseEntity.ok(userService.update(userDto.dtoToEntity(userDto),id));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getUserDetails(@PathVariable int id) {

        return ResponseEntity.ok(new ResponseDTO("user fetch ok", true, userService.read(id)));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getAllUsers() {

        return ResponseEntity.ok(new ResponseDTO("All user fetch ok", true, userService.read()));
    }

}
