package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.service.personnelmanagement.PersonalAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.aes.corebackend.util.response.APIResponse;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonalAddressController {

    private final PersonalAddressService personalAddressService;

    private static final Logger logger = LoggerFactory.getLogger(PersonalAddressController.class);

    @PostMapping(value = "/users/{userId}/personal-address")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> createPersonalAddress(@Valid @RequestBody PersonalAddressInfoDTO addressInfoDTO, @PathVariable Long userId) {

        APIResponse apiResponse = personalAddressService.create(addressInfoDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @PutMapping(value = "/users/{userId}/personal-address")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> updatePersonalAddress(@Valid @RequestBody PersonalAddressInfoDTO personalAddressInfoDTO, @PathVariable Long userId) {

        APIResponse apiResponse = personalAddressService.update(personalAddressInfoDTO, userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }

    @GetMapping(value = "/users/{userId}/personal-address")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'SYS_ADMIN')")
    public ResponseEntity<?> getPersonalAddress(@PathVariable Long userId) {

        APIResponse apiResponse = personalAddressService.read(userId);
        return apiResponse.isSuccess() ? ok(apiResponse) : badRequest().body(apiResponse);
    }
}
