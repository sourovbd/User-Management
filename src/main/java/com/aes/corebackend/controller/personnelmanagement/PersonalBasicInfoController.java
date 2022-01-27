package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.service.UserService;
import com.aes.corebackend.service.personnelmanagement.PersonalInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PersonalBasicInfoController {
    @Autowired
    UserService userService;

    @Autowired
    PersonalInformationService personalInformationService;

    @PostMapping(value = "/users/{userId}/create-personal-basic-info")
    public ResponseEntity<?> createPersonalBasicInfo(@RequestBody PersonalBasicInfoDTO personalBasicInfoDTO, @PathVariable Long userId) {
        PersonalBasicInfo basicInfo = personalBasicInfoDTO.getPersonalBasicInfoEntity(personalBasicInfoDTO);
        PersonnelManagementResponseDTO response = personalInformationService.createPersonalBasicInfo(basicInfo, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/users/{userId}/update-personal-basic-info")
    public ResponseEntity<?> updatePersonalBasicInfo(@RequestBody PersonalBasicInfoDTO basicInfoDTO, @PathVariable Long userId) {
        PersonalBasicInfo basicInfo = basicInfoDTO.getPersonalBasicInfoEntity(basicInfoDTO);
        PersonnelManagementResponseDTO response = personalInformationService.updatePersonalBasicInfo(basicInfo, userId);
        return ResponseEntity.ok(response);
    }
}
