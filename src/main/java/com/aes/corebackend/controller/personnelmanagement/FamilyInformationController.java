package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.service.personnelmanagement.FamilyInformationService;
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
public class FamilyInformationController {

    private final FamilyInformationService familyInformationService;

    private static final Logger logger = LoggerFactory.getLogger(FamilyInformationController.class);

    @PostMapping(value = "/users/{userId}/family-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> createFamilyInfo(@Valid @RequestBody PersonalFamilyInfoDTO familyInfoDTO, @PathVariable Long userId) {
       /* if(result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }*/
        APIResponse apiResponse = familyInformationService.create(familyInfoDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/family-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updateFamilyInfo(@Valid @RequestBody PersonalFamilyInfoDTO familyInfoDTO, @PathVariable Long userId) {
        APIResponse apiResponse = familyInformationService.update(familyInfoDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/family-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getFamilyInfo(@PathVariable Long userId) {
        APIResponse apiResponse =familyInformationService.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}
