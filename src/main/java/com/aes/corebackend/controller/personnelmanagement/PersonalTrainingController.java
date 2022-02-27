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
import com.aes.corebackend.util.response.APIResponse;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static com.aes.corebackend.util.response.APIResponse.prepareErrorResponse;

@RestController
@RequiredArgsConstructor
public class PersonalTrainingController {

    private final PersonalTrainingService personalTrainingService;

    @PostMapping(value = "/users/{userId}/trainings")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> createPersonalTraining(@Valid @RequestBody PersonalTrainingDTO personalTrainingDTO, @PathVariable Long userId) {

        APIResponse apiResponse = personalTrainingService.create(personalTrainingDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/trainings/{trainingId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updatePersonalTraining(@Valid @RequestBody PersonalTrainingDTO personalTrainingDTO, @PathVariable Long userId, @PathVariable Long trainingId) {

        APIResponse apiResponse = personalTrainingService.update(personalTrainingDTO, userId, trainingId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/trainings")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalTrainings(@PathVariable Long userId) {

        APIResponse apiResponse = personalTrainingService.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/trainings/{trainingId}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalTraining(@PathVariable Long userId, @PathVariable Long trainingId) {

        APIResponse apiResponse = personalTrainingService.read(userId, trainingId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}
