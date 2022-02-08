package com.aes.corebackend.controller;

import com.aes.corebackend.dto.*;
import com.aes.corebackend.service.UserService;
import com.aes.corebackend.util.response.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.aes.corebackend.util.response.APIResponse.prepareErrorResponse;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** static is used so that it only happens once */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO userDto, BindingResult result) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = userService.create(userDto.dtoToEntity(userDto),userDto);

        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updateUser(@RequestBody @Valid  UserDTO userDto, @PathVariable long id) {
        APIResponse apiResponse = userService.update(userDto,id);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getUserDetails(@PathVariable int id) {
        APIResponse apiResponse = userService.read(id);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getAllUsers() {
        APIResponse apiResponse = userService.read();
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}
