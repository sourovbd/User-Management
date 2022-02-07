package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalIdentificationInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalIdentificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PersonalIdentificationController {

    private final PersonalIdentificationService service;

    @PostMapping(value = "/users/{userId}/identification-information")
    public ResponseEntity<?> createAttributesInfo(@Valid @RequestBody PersonalIdentificationInfoDTO idDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(service.create(idDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/identification-information")
    public ResponseEntity<?> updateAttributesInfo(@Valid @RequestBody PersonalIdentificationInfoDTO idDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(service.update(idDTO, userId));
    }


    @GetMapping(value = "/users/{userId}/identification-information")
    public ResponseEntity<?> getAttributesInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(service.read(userId));
    }

}
