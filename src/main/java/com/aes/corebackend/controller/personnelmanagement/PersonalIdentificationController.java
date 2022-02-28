package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalIdentificationInfoDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalIdentificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonalIdentificationController {

    private final PersonalIdentificationService service;

    private static final Logger logger = LoggerFactory.getLogger(PersonalIdentificationController.class);

    @PostMapping(value = "/users/{userId}/identification-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> createIdentificationInfo(@Valid @RequestBody PersonalIdentificationInfoDTO idDTO, @PathVariable Long userId) {

        APIResponse apiResponse = service.create(idDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/identification-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updateIdentificationInfo(@Valid @RequestBody PersonalIdentificationInfoDTO idDTO, @PathVariable Long userId) {

        APIResponse apiResponse = service.update(idDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }


    @GetMapping(value = "/users/{userId}/identification-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getIdentificationInfo(@PathVariable Long userId) {

        APIResponse apiResponse = service.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

}
