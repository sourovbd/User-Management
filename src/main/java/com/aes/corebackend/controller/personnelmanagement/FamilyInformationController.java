package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.service.UserService;
import com.aes.corebackend.service.personnelmanagement.FamilyInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller
public class FamilyInformationController {
    @Autowired
    FamilyInformationService familyInformationService;
    @Autowired
    UserService userService;

    @PostMapping(value = "/users/create/family-info/{userId}")
    public ResponseEntity<?> createFamilyInfo(@RequestBody PersonalFamilyInfoDTO familyInfoDTO, @PathVariable Long userId) {
        boolean success = false;
        String message = familyInformationService.createPersonalFamilyInfo(familyInfoDTO, userId);
        return ResponseEntity.ok(new PersonnelManagementResponseDTO(message, success));
    }

    @PutMapping(value = "/users/update/family-info/{userId}")
    public ResponseEntity<?> updateFamilyInfo(@RequestBody PersonalFamilyInfoDTO familyInfoDTO, @PathVariable Long userId) {

        String message = familyInformationService.updatePersonalFamilyInfo(familyInfoDTO, userId);
/*
        PersonalFamilyInfo familyInfo = familyInfoDTO.getPersonalFamilyEntity(familyInfoDTO);
        User user = userService.getUserByUserId(userId);

        if(Objects.nonNull(user)){
            familyInfo.setUser(user);
            success = familyInformationService.updatePersonalFamilyInfo(familyInfo);
            if(success){
                message = "Update Successful";
            }
        }else{
            message = "User not found!";
        }*/
        return ResponseEntity.ok(new PersonnelManagementResponseDTO(message, false));
    }
}
