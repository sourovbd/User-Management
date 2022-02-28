package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;

import com.aes.corebackend.service.personnelmanagement.PersonalEducationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonalEducationController {

    private final PersonalEducationService service;

    private static final Logger logger = LoggerFactory.getLogger(PersonalEducationController.class);

    @PostMapping(value = "/users/{userId}/education-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> createPersonalEducation(@RequestBody @Valid PersonalEducationDTO educationDTO, @PathVariable Long userId) {

        APIResponse apiResponse = service.create(educationDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/education-information/{educationId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updatePersonalEducation(@RequestBody @Valid PersonalEducationDTO educationDTO, @PathVariable Long userId, @PathVariable Long educationId) {

        APIResponse apiResponse = service.update(educationDTO, userId, educationId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/education-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalEducationInfomations(@PathVariable Long userId) {

        APIResponse apiResponse =service.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/education-information/{educationId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalEducation(@PathVariable Long userId, @PathVariable Long educationId) {

        APIResponse apiResponse =service.read(userId, educationId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}
