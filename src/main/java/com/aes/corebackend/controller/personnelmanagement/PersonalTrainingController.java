package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonalTrainingController {
    @Autowired
    PersonalTrainingService personalTrainingService;

    @PostMapping(value = "/users/{userId}/trainings")
    public ResponseEntity<?> createPersonalTraining(@RequestBody PersonalTrainingDTO personalTrainingDTO, @PathVariable Long userId) {
        PersonnelManagementResponseDTO response = personalTrainingService.create(personalTrainingDTO, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/users/{userId}/trainings/{trainingId}")
    public ResponseEntity<?> updatePersonalTraining(@RequestBody PersonalTrainingDTO personalTrainingDTO, @PathVariable Long userId, @PathVariable Long trainingId) {
        PersonnelManagementResponseDTO response = personalTrainingService.update(personalTrainingDTO, userId, trainingId);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users/{userId}/trainings")
    public ResponseEntity<?> getPersonalTrainings(@PathVariable Long userId) {
        PersonnelManagementResponseDTO response = personalTrainingService.read(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users/{userId}/trainings/{trainingId}")
    public ResponseEntity<?> getPersonalTraining(@PathVariable Long userId, @PathVariable Long trainingId) {
        PersonnelManagementResponseDTO response = personalTrainingService.read(userId, trainingId);
        return ResponseEntity.ok(response);
    }
}
