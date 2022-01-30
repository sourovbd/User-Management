package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PersonalAddressController {

    @Autowired
    PersonalAddressService personalAddressService;

    @PostMapping(value = "/users/{userId}/personal-address")
    public ResponseEntity<?> createPersonalAddress(@RequestBody PersonalAddressInfoDTO addressInfoDTO, @PathVariable Long userId) {
        System.out.println("addressInfoDTO");
        System.out.println(addressInfoDTO);
        PersonnelManagementResponseDTO response = personalAddressService.createPersonalAddress(addressInfoDTO, userId);
        return ResponseEntity.ok(response);
    }
}
