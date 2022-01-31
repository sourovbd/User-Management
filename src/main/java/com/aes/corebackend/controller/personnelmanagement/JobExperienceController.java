package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalJobExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobExperienceController {

    @Autowired
    PersonalJobExperienceService personalJobExperienceService;

    @PostMapping(value = "/users/{userId}/job-experience")
    public ResponseEntity<?> createJobExperience(@RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, @PathVariable Long userId) {
        PersonnelManagementResponseDTO response = personalJobExperienceService.create(personalJobExperienceDTO, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/users/{userId}/job-experience/{experienceId}")
    public ResponseEntity<?> updatePersonalBasicInfo(@RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, @PathVariable Long userId, @PathVariable Long experienceId) {
        System.out.println("userId: " + userId);
        System.out.println("experienceId: " + experienceId);
        PersonnelManagementResponseDTO response = personalJobExperienceService.update(personalJobExperienceDTO, userId, experienceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users/{userId}/job-experiences")
    public ResponseEntity<?> getPersonalJobExperiences(@PathVariable Long userId) {
        PersonnelManagementResponseDTO response = personalJobExperienceService.getJobExperiences(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users/{userId}/job-experiences/{experienceId}")
    public ResponseEntity<?> getPersonalJobExperience(@PathVariable Long userId, @PathVariable Long experienceId) {
        PersonnelManagementResponseDTO response = personalJobExperienceService.getJobExperience(userId, experienceId);
        return ResponseEntity.ok(response);
    }
}
