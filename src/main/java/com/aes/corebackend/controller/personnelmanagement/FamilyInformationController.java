package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.service.personnelmanagement.FamilyInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class FamilyInformationController {
    private final FamilyInformationService familyInformationService;

    @PostMapping(value = "/users/{userId}/family-information")
    public ResponseEntity<?> createFamilyInfo(@RequestBody PersonalFamilyInfoDTO familyInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(familyInformationService.create(familyInfoDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/family-information")
    public ResponseEntity<?> updateFamilyInfo(@RequestBody PersonalFamilyInfoDTO familyInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(familyInformationService.update(familyInfoDTO, userId));
    }

    @GetMapping(value = "/users/{userId}/family-information")
    public ResponseEntity<?> getFamilyInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(familyInformationService.read(userId));
    }
}
