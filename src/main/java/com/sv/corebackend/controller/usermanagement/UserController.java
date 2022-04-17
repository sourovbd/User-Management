package com.sv.corebackend.controller.usermanagement;

import com.sv.corebackend.dto.usermanagement.UserDTO;
import com.sv.corebackend.service.usermanagement.UserService;
import com.sv.corebackend.util.response.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * static is used so that it only happens once
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDto) {
        APIResponse apiResponse = userService.create(userDto.dtoToEntity(userDto), userDto);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO userDto, @PathVariable long id) {
        APIResponse apiResponse = userService.update(userDto, id);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getUserDetails(@PathVariable int id, Authentication authentication) {

        APIResponse apiResponse = userService.read(id);
        if (apiResponse.isSuccess()) {
            return ok(apiResponse);
        }
        return badRequest().body(apiResponse);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        APIResponse apiResponse = userService.read();
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}
