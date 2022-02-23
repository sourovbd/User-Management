package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.service.personnelmanagement.PersonalAttributesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.aes.corebackend.util.response.APIResponse.prepareErrorResponse;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequiredArgsConstructor
public class PersonalAttributesController {

    private final PersonalAttributesService personalAttributesService;

    @PostMapping(value = "/users/{userId}/attribute-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> createPersonalAttributes(@Valid @RequestBody PersonalAttributesDTO attributesDTO, BindingResult result, @PathVariable Long userId) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = personalAttributesService.create(attributesDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/attribute-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updatePersonalAttributes(@Valid @RequestBody PersonalAttributesDTO attributesDTO, BindingResult result, @PathVariable Long userId) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = personalAttributesService.update(attributesDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/attribute-information")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalAttributes(@PathVariable Long userId) {
        APIResponse apiResponse =personalAttributesService.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

}
