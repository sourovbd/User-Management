package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PersonalTrainingController {

    private final PersonalTrainingService personalTrainingService;

    @PostMapping(value = "/users/{userId}/trainings")
    public ResponseEntity<?> createPersonalTraining(@RequestBody PersonalTrainingDTO personalTrainingDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(personalTrainingService.create(personalTrainingDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/trainings/{trainingId}")
    public ResponseEntity<?> updatePersonalTraining(@RequestBody PersonalTrainingDTO personalTrainingDTO, @PathVariable Long userId, @PathVariable Long trainingId) {
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
