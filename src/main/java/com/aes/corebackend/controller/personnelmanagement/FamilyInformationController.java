package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.service.UserService;
import com.aes.corebackend.service.personnelmanagement.FamilyInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller
public class FamilyInformationController {
    @Autowired
    FamilyInformationService familyInformationService;
    @Autowired
    UserService userService;

    @PostMapping(value = "/users/{userId}/create-family-info")
    public ResponseEntity<?> createFamilyInfo(@RequestBody PersonalFamilyInfoDTO familyInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(familyInformationService.createPersonalFamilyInfo(familyInfoDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/update-family-info")
    public ResponseEntity<?> updateFamilyInfo(@RequestBody PersonalFamilyInfoDTO familyInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(familyInformationService.updatePersonalFamilyInfo(familyInfoDTO, userId));
    }
}
