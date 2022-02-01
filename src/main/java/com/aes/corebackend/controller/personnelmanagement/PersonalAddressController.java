package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonalAddressController {

    @Autowired
    PersonalAddressService personalAddressService;

    @PostMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> createPersonalAddress(@RequestBody PersonalAddressInfoDTO addressInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalAddressService.createPersonalAddress(addressInfoDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> updatePersonalAddress(@RequestBody PersonalAddressInfoDTO personalAddressInfoDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalAddressService.updatePersonalAddress(personalAddressInfoDTO, userId));
    }

    @GetMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> getPersonalAddress(@PathVariable Long userId) {
        return ResponseEntity.ok(personalAddressService.getPersonalAddressInfo(userId));
    }
}
