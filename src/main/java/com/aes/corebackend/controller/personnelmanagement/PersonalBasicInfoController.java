package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalBasicInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PersonalBasicInfoController {

    private final PersonalBasicInformationService personalBasicInformationService;

    @PostMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> createPersonalBasicInfo(@RequestBody @Valid PersonalBasicInfoDTO personalBasicInfoDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            System.out.println("has errors in post basic info");
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalBasicInformationService.create(personalBasicInfoDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> updatePersonalBasicInfo(@Valid @RequestBody PersonalBasicInfoDTO basicInfoDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalBasicInformationService.update(basicInfoDTO, userId));
    }

    @GetMapping(value = "/users/{userId}/basic-information")
    public ResponseEntity<?> getPersonalBasicInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(personalBasicInformationService.read(userId));
    }
}
