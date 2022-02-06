package com.aes.corebackend.controller;

import com.aes.corebackend.dto.*;
import com.aes.corebackend.service.UserCredentialService;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.aes.corebackend.util.response.AjaxResponse.prepareErrorResponse;
import static org.springframework.http.ResponseEntity.badRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserCredentialService userCredentialService;

    /** static is used so that it only happens once */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO userDto, BindingResult result) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        return userService.create(userDto.dtoToEntity(userDto),userDto);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updateUser(@RequestBody @Valid  UserDTO userDto, @PathVariable long id) {

        return ResponseEntity.ok(userService.update(userDto.dtoToEntity(userDto),id));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getUserDetails(@PathVariable int id) {

        return ResponseEntity.ok(userService.read(id));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getAllUsers() {

        return ResponseEntity.ok(userService.read());
    }

}
