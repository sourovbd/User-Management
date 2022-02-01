package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalBasicInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class PersonalBasicInfoController {

    @Autowired
    PersonalBasicInformationService personalBasicInformationService;

    @PostMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> createPersonalBasicInfo(@RequestBody PersonalBasicInfoDTO personalBasicInfoDTO, @PathVariable Long userId) {
        PersonnelManagementResponseDTO response = personalBasicInformationService.createPersonalBasicInfo(personalBasicInfoDTO, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> updatePersonalBasicInfo(@RequestBody PersonalBasicInfoDTO basicInfoDTO, @PathVariable Long userId) {
        PersonnelManagementResponseDTO response = personalBasicInformationService.updatePersonalBasicInfo(basicInfoDTO, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> getPersonalBasicInfo(@PathVariable Long userId) {
        PersonnelManagementResponseDTO response = personalBasicInformationService.getPersonalBasicInfo(userId);
        return ResponseEntity.ok(response);
    }
}
