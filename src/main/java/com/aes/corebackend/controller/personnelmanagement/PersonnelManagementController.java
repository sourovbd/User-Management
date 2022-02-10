package com.aes.corebackend.controller.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.*;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.*;
import com.aes.corebackend.service.UserService;
import com.aes.corebackend.service.personnelmanagement.PersonalInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PersonnelManagementController {

    @Autowired
    PersonalInformationService personalInformationService;
    @Autowired
    UserService userService;

    @PostMapping(value = "/users/{id}/update-personal-identification-info")
    public ResponseEntity<?> updatePersonalIdentificationInfo(@RequestBody PersonalIdentificationInfoDTO identificationInfoDTO, @PathVariable String id) {
        //TODO convert dto to entity
        PersonalIdentificationInfo identificationInfo = identificationInfoDTO.getPersonalIdentificationEntity(identificationInfoDTO);
        boolean success = personalInformationService.updatePersonalIdentificationInfo(identificationInfo);
        return ResponseEntity.ok(new PersonnelManagementResponseDTO("Update successfull!", success, null));
    }
/*
    @PostMapping(value = "/users/{id}/update-personal-education")
    public ResponseEntity<?> updatePersonalEducation(@RequestBody PersonalEducationDTO educationDTO, @PathVariable String id) {
        //TODO convert dto to entity
        PersonalEducationInfo educationInfo = educationDTO.getPersonalEducationEntity(educationDTO);
        boolean success = personalInformationService.updatePersonalEducation(educationInfo);
        return ResponseEntity.ok(new PersonnelManagementResponseDTO("Update successfull!", success, null));
    }
*/
    @PostMapping(value = "/users/{id}/update-personal-training")
    public ResponseEntity<?> updatePersonalTraining(@RequestBody PersonalTrainingDTO trainingDTO, @PathVariable String id) {
        //TODO convert dto to entity
        PersonalTrainingInfo trainingInfo = trainingDTO.getPersonalTrainingEntity(trainingDTO);
        boolean success = personalInformationService.updatePersonalTraining(trainingInfo);
        return ResponseEntity.ok(new PersonnelManagementResponseDTO("Update successfull!", success, null));
    }

    @PostMapping(value = "/users/{id}/update-personal-job-experience")
    public ResponseEntity<?> updateJobExperience(@RequestBody PersonalJobExperienceDTO experienceDTO, @PathVariable String id) {
        //TODO convert dto to entity
        PersonalJobExperience experienceInfo = experienceDTO.getPersonalJobExperienceEntity(experienceDTO);
        boolean success = personalInformationService.updateJobExperience(experienceInfo);
        return ResponseEntity.ok(new PersonnelManagementResponseDTO("Update successfull!", success, null));
    }

    //Will need one more GET request to respond back everything
}