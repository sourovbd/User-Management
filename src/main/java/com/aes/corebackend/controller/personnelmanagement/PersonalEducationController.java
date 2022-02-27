package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;

import com.aes.corebackend.service.personnelmanagement.PersonalEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.aes.corebackend.util.response.APIResponse.prepareErrorResponse;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class PersonalEducationController {

    private final PersonalEducationService service;

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
