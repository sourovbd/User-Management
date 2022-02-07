package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PersonalTrainingController {

    private final PersonalTrainingService personalTrainingService;

    @PostMapping(value = "/users/{userId}/trainings")
    public ResponseEntity<?> createPersonalTraining(@Valid @RequestBody PersonalTrainingDTO personalTrainingDTO, BindingResult result, @PathVariable Long userId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalTrainingService.create(personalTrainingDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/trainings/{trainingId}")
    public ResponseEntity<?> updatePersonalTraining(@Valid @RequestBody PersonalTrainingDTO personalTrainingDTO, BindingResult result, @PathVariable Long userId, @PathVariable Long trainingId) {
        if(result.hasErrors()){
            return ResponseEntity.ok(new PersonnelManagementResponseDTO(result.getFieldError().getDefaultMessage(), false, null));
        }
        return ResponseEntity.ok(personalTrainingService.update(personalTrainingDTO, userId, trainingId));
    }

    @GetMapping(value = "/users/{userId}/trainings")
    public ResponseEntity<?> getPersonalTrainings(@PathVariable Long userId) {
        return ResponseEntity.ok(personalTrainingService.read(userId));
    }

    @GetMapping(value = "/users/{userId}/trainings/{trainingId}")
    public ResponseEntity<?> getPersonalTraining(@PathVariable Long userId, @PathVariable Long trainingId) {
        return ResponseEntity.ok(personalTrainingService.read(userId, trainingId));
    }
}
