package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalJobExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.aes.corebackend.util.response.APIResponse;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobExperienceController {

    private final PersonalJobExperienceService personalJobExperienceService;

    private static final Logger logger = LoggerFactory.getLogger(JobExperienceController.class);

    @PostMapping(value = "/users/{userId}/job-experiences")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> createJobExperience(@Valid @RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, @PathVariable Long userId) {

        APIResponse apiResponse = personalJobExperienceService.create(personalJobExperienceDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/job-experiences/{experienceId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updatePersonalJobExperience(@Valid @RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, @PathVariable Long userId, @PathVariable Long experienceId) {

        APIResponse apiResponse = personalJobExperienceService.update(personalJobExperienceDTO, userId, experienceId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/job-experiences")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalJobExperiences(@PathVariable Long userId) {

        APIResponse apiResponse = personalJobExperienceService.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/job-experiences/{experienceId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalJobExperience(@PathVariable Long userId, @PathVariable Long experienceId) {

        APIResponse apiResponse = personalJobExperienceService.read(userId, experienceId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}
