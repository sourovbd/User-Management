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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller
public class PersonalAttributesController {
    @Autowired
    PersonalAttributesService personalAttributesService;
    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/users/{userId}/create-attributes-info")
    public ResponseEntity<?> createAttributesInfo(@RequestBody PersonalAttributesDTO attributesDTO, @PathVariable Long userId) {
        String message = "Create Failed";
        boolean success = false;
/*
        PersonalAttributes attributes = attributesDTO.getPersonalAttributesEntity(attributesDTO);
        User user = userRepository.getById(userId);

        if(Objects.nonNull(user)){
            attributes.setUser(user);
            success = personalAttributesService.createAttributesInfo(attributes);
            if(success){
                message = "Create Successful";
            }
        }else{
            message = "User not found!";
        }
*/
        return ResponseEntity.ok(new PersonnelManagementResponseDTO(message, success, null));
    }

    @PutMapping(value = "/users/{userId}/update-attributes-info")
    public ResponseEntity<?> updateAttributesInfo(@RequestBody PersonalAttributesDTO attributesDTO, @PathVariable Long userId) {
        String message = "Update Failed";
        boolean success = false;
        PersonalAttributes attributes = attributesDTO.getPersonalAttributesEntity(attributesDTO);
        User user = userRepository.getById(userId);

        if(Objects.nonNull(user)){
            attributes.setUser(user);
            success = personalAttributesService.updateAttributesInfo(attributes);
            if(success){
                message = "Update Successful";
            }
        }else{
            message = "User not found!";
        }

        return ResponseEntity.ok(new PersonnelManagementResponseDTO(message, success, null));
    }

}
