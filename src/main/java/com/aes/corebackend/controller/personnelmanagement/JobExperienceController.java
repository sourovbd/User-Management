package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalJobExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class JobExperienceController {


    private final PersonalJobExperienceService personalJobExperienceService;

    @PostMapping(value = "/users/{userId}/job-experiences")
    public ResponseEntity<?> createJobExperience(@Valid @RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalJobExperienceService.create(personalJobExperienceDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/job-experiences/{experienceId}")
    public ResponseEntity<?> updatePersonalJobExperience(@Valid @RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, BindingResult result, @PathVariable Long userId, @PathVariable Long experienceId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalJobExperienceService.update(personalJobExperienceDTO, userId, experienceId));
    }

    @GetMapping(value = "/users/{userId}/job-experiences")
    public ResponseEntity<?> getPersonalJobExperiences(@PathVariable Long userId) {
        return ResponseEntity.ok(personalJobExperienceService.read(userId));
    }

    @GetMapping(value = "/users/{userId}/job-experiences/{experienceId}")
    public ResponseEntity<?> getPersonalJobExperience(@PathVariable Long userId, @PathVariable Long experienceId) {
        return ResponseEntity.ok(personalJobExperienceService.read(userId, experienceId));
    }
}
