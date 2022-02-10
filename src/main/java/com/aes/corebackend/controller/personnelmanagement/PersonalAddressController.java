package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
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

@Controller
@RequiredArgsConstructor
public class PersonalAddressController {

    private final PersonalAddressService personalAddressService;

    @PostMapping(value = "/users/{userId}/personal-address")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> createPersonalAddress(@Valid @RequestBody PersonalAddressInfoDTO addressInfoDTO, BindingResult result, @PathVariable Long userId) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = personalAddressService.create(addressInfoDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/personal-address")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> updatePersonalAddress(@Valid @RequestBody PersonalAddressInfoDTO personalAddressInfoDTO, BindingResult result, @PathVariable Long userId) {
        if (result.hasErrors()) {
            return badRequest().body(prepareErrorResponse(result));
        }
        APIResponse apiResponse = personalAddressService.update(personalAddressInfoDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/personal-address")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> getPersonalAddress(@PathVariable Long userId) {
        APIResponse apiResponse = personalAddressService.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}