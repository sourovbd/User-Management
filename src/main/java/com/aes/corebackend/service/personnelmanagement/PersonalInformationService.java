package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.*;
import com.aes.corebackend.repository.personnelmanagement.*;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PersonalInformationService {

    @Autowired
    PersonalIdentificationInfoRepository personalIdentificationInfoRepository;
    @Autowired
    PersonalAddressInfoRepository personalAddressInfoRepository;
    @Autowired
    PersonalEducationRepository personalEducationRepository;
    @Autowired
    PersonalTrainingRepository personalTrainingRepository;
    @Autowired
    PersonalJobExperienceRepository personalJobExperienceRepository;
    @Autowired
    UserService userService;

    public boolean updatePersonalIdentificationInfo(PersonalIdentificationInfo identificationInfo) {
        try {
            personalIdentificationInfoRepository.save(identificationInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updatePersonalEducation(PersonalEducationInfo educationInfo) {
        try {
            personalEducationRepository.save(educationInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updatePersonalTraining(PersonalTrainingInfo trainingInfo) {
        try {
            personalTrainingRepository.save(trainingInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateJobExperience(PersonalJobExperience jobExperience) {
        try {
            personalJobExperienceRepository.save(jobExperience);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
