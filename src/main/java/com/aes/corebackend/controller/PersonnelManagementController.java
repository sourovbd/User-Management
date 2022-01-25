package com.aes.corebackend.controller;

import com.aes.corebackend.dto.PersonalInformationDTO;
import com.aes.corebackend.dto.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.PersonalInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PersonnelManagementController {
    PersonalInformationService personalInformationService;
    @PostMapping(value = "/users/{id}/update-personal-information")
    public ResponseEntity<?> updatePersonalInformation(@RequestBody PersonalInformationDTO personalInfo, @PathVariable String id) {
        //TODO convert dto to entity
        personalInformationService.updatePersonalInformation(personalInfo.from(personalInfo));
        return ResponseEntity.ok(new PersonnelManagementResponseDTO("Update successfull!", true));
    }
}
