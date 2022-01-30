package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class JobExperienceController {

    @PostMapping(value = "/users/{userId}/job-experience")
    public ResponseEntity<?> createJobExperience(@RequestBody PersonalJobExperienceDTO personalJobExperienceDTO, @PathVariable Long userId) {

    }
}
