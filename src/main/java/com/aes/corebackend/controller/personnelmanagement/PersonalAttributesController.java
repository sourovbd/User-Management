package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.repository.UserRepository;
import com.aes.corebackend.repository.personnelmanagement.PersonalAttributesRepository;
import com.aes.corebackend.service.personnelmanagement.PersonalAttributesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class PersonalAttributesController {

    private final PersonalAttributesService personalAttributesService;

    @PostMapping(value = "/users/{userId}/attribute-information")
    public ResponseEntity<?> createAttributesInfo(@Valid @RequestBody PersonalAttributesDTO attributesDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalAttributesService.create(attributesDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/attribute-information")
    public ResponseEntity<?> updateAttributesInfo(@Valid @RequestBody PersonalAttributesDTO attributesDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalAttributesService.update(attributesDTO, userId));
    }

    @GetMapping(value = "/users/{userId}/attribute-information")
    public ResponseEntity<?> getPersonalBasicInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(personalAttributesService.read(userId));
    }

}
