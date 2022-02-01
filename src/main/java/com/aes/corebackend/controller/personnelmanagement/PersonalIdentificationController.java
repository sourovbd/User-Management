package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalIdentificationInfoDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonalIdentificationController {
    @Autowired
    PersonalIdentificationService service;

    @PostMapping(value = "/users/{userId}/identification-information")
    public ResponseEntity<?> createAttributesInfo(@RequestBody PersonalIdentificationInfoDTO idDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(service.create(idDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/identification-information")
    public ResponseEntity<?> updateAttributesInfo(@RequestBody PersonalIdentificationInfoDTO idDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(service.update(idDTO, userId));
    }


    @GetMapping(value = "/users/{userId}/identification-information")
    public ResponseEntity<?> getAttributesInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(service.read(userId));
    }

}
