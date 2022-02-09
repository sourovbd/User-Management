package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.aes.corebackend.util.response.AjaxResponse.prepareErrorResponse;
import static org.springframework.http.ResponseEntity.badRequest;

@Controller
@RequiredArgsConstructor
public class PersonalTrainingController {

    private final PersonalTrainingService personalTrainingService;

    @PostMapping(value = "/users/{userId}/trainings")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> createPersonalTraining(@Valid @RequestBody PersonalTrainingDTO personalTrainingDTO, BindingResult result, @PathVariable Long userId) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        return ResponseEntity.ok(personalTrainingService.create(personalTrainingDTO, userId));
    }

    @PutMapping(value = "/users/{userId}/trainings/{trainingId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updatePersonalTraining(@Valid @RequestBody PersonalTrainingDTO personalTrainingDTO, BindingResult result, @PathVariable Long userId, @PathVariable Long trainingId) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        return ResponseEntity.ok(personalTrainingService.update(personalTrainingDTO, userId, trainingId));
    }

    @GetMapping(value = "/users/{userId}/trainings")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getPersonalTrainings(@PathVariable Long userId) {
        return ResponseEntity.ok(personalTrainingService.read(userId));
    }

    @GetMapping(value = "/users/{userId}/trainings/{trainingId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getPersonalTraining(@PathVariable Long userId, @PathVariable Long trainingId) {
        return ResponseEntity.ok(personalTrainingService.read(userId, trainingId));
    }
}
