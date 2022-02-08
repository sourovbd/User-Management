package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;

import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PersonalEducationController {
    private final PersonalEducationService service;

    @PostMapping(value = "/users/{userId}/education-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updatePersonalEducation(@RequestBody @Valid PersonalEducationDTO educationDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(service.create(educationDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/education-information/{educationId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updatePersonalEducation(@RequestBody @Valid PersonalEducationDTO educationDTO, BindingResult result, @PathVariable Long userId, @PathVariable Long educationId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(service.update(educationDTO, userId, educationId));
    }


    @GetMapping(value = "/users/{userId}/education-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalEducationInfomations(@PathVariable Long userId) {
        return ResponseEntity.ok(service.read(userId));
    }

    @GetMapping(value = "/users/{userId}/education-information/{educationId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalEducation(@PathVariable Long userId, @PathVariable Long educationId) {
        return ResponseEntity.ok(service.read(userId, educationId));
    }
}
