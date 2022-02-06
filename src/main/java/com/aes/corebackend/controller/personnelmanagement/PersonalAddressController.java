package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PersonalAddressController {

    private final PersonalAddressService personalAddressService;

    @PostMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> createPersonalAddress(@Valid @RequestBody PersonalAddressInfoDTO addressInfoDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalAddressService.create(addressInfoDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> updatePersonalAddress(@Valid @RequestBody PersonalAddressInfoDTO personalAddressInfoDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalAddressService.update(personalAddressInfoDTO, userId));
    }

    @GetMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> getPersonalAddress(@PathVariable Long userId) {
        return ResponseEntity.ok(personalAddressService.read(userId));
    }
}
