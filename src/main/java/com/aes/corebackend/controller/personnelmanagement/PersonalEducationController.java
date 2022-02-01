package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;

import com.aes.corebackend.service.personnelmanagement.PersonalEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonalEducationController {
    @Autowired
    PersonalEducationService service;

    @PostMapping(value = "/users/{userId}/education-info")
    public ResponseEntity<?> updatePersonalEducation(@RequestBody PersonalEducationDTO educationDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(service.create(educationDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/education-info/{educationId}")
    public ResponseEntity<?> updatePersonalEducation(@RequestBody PersonalEducationDTO educationDTO, @PathVariable Long userId, @PathVariable Long educationId) {
        return ResponseEntity.ok(service.update(educationDTO, userId, educationId));
    }


    @GetMapping(value = "/users/{userId}/education-infos")
    public ResponseEntity<?> getPersonalEducationInfomations(@PathVariable Long userId) {
        return ResponseEntity.ok(service.read(userId));
    }

    @GetMapping(value = "/users/{userId}/education-info/{educationId}")
    public ResponseEntity<?> getPersonalEducation(@PathVariable Long userId, @PathVariable Long educationId) {
        return ResponseEntity.ok(service.read(userId, educationId));
    }
}
