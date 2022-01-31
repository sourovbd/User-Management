package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.repository.UserRepository;
import com.aes.corebackend.repository.personnelmanagement.PersonalAttributesRepository;
import com.aes.corebackend.service.personnelmanagement.PersonalAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class PersonalAttributesController {
    @Autowired
    PersonalAttributesService personalAttributesService;

    @PostMapping(value = "/users/{userId}/create-attributes-info")
    public ResponseEntity<?> createAttributesInfo(@RequestBody PersonalAttributesDTO attributesDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalAttributesService.createAttributesInfo(attributesDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/update-attributes-info")
    public ResponseEntity<?> updateAttributesInfo(@RequestBody PersonalAttributesDTO attributesDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalAttributesService.updateAttributesInfo(attributesDTO, userId));
    }

    @GetMapping(value = "/users/{userId}/attributes-info")
    public ResponseEntity<?> getPersonalBasicInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(personalAttributesService.getPersonalAttributes(userId));
    }

}
