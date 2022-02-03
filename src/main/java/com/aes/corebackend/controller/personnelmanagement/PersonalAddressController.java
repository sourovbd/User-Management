package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PersonalAddressController {

    private final PersonalAddressService personalAddressService;

    @PostMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> createPersonalAddress(@RequestBody PersonalAddressInfoDTO addressInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalAddressService.create(addressInfoDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> updatePersonalAddress(@RequestBody PersonalAddressInfoDTO personalAddressInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalAddressService.update(personalAddressInfoDTO, userId));
    }

    @GetMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> getPersonalAddress(@PathVariable Long userId) {
        return ResponseEntity.ok(personalAddressService.read(userId));
    }
}
