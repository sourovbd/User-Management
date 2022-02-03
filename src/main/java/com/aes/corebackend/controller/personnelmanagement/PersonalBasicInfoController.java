package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalBasicInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PersonalBasicInfoController {

    @Autowired
    PersonalBasicInformationService personalBasicInformationService;

    @PostMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> createPersonalBasicInfo(@RequestBody @Valid PersonalBasicInfoDTO personalBasicInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalBasicInformationService.create(personalBasicInfoDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> updatePersonalBasicInfo(@RequestBody PersonalBasicInfoDTO basicInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalBasicInformationService.update(basicInfoDTO, userId));
    }

    @GetMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> getPersonalBasicInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(personalBasicInformationService.read(userId));
    }
}
