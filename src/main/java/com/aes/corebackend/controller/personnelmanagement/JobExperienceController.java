package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalJobExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.aes.corebackend.util.response.APIResponse;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static com.aes.corebackend.util.response.APIResponse.prepareErrorResponse;

@Controller
@RequiredArgsConstructor
public class JobExperienceController {


    private final PersonalJobExperienceService personalJobExperienceService;

    @PostMapping(value = "/users/{userId}/job-experiences")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> createJobExperience(@Valid @RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, BindingResult result, @PathVariable Long userId) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = personalJobExperienceService.create(personalJobExperienceDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/job-experiences/{experienceId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updatePersonalJobExperience(@Valid @RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, BindingResult result, @PathVariable Long userId, @PathVariable Long experienceId) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = personalJobExperienceService.update(personalJobExperienceDTO, userId, experienceId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/job-experiences")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getPersonalJobExperiences(@PathVariable Long userId) {
        APIResponse apiResponse = personalJobExperienceService.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/job-experiences/{experienceId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getPersonalJobExperience(@PathVariable Long userId, @PathVariable Long experienceId) {
        APIResponse apiResponse = personalJobExperienceService.read(userId, experienceId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}
