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
    PersonalBasicInfoRepository personalBasicInfoRepository;
    @Autowired
    PersonalAttributesRepository personalAttributesRepository;
    @Autowired
    PersonalFamilyInfoRepository personalFamilyInfoRepository;
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

    public PersonnelManagementResponseDTO createPersonalBasicInfo(PersonalBasicInfo basicInfo, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Basic information creation successful", true);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            basicInfo.setUser(user);
            boolean success = this.createPersonalBasicInfo(basicInfo);
            if (!success) {
                response.setMessage("Basic information creation failed");
            }
        } else {
            response.setMessage("User not found");
        }
        return response;
    }

    private boolean createPersonalBasicInfo(PersonalBasicInfo basicInfo) {
        try {
            personalBasicInfoRepository.save(basicInfo);//create new row into basic info table
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private PersonalBasicInfo getPersonalBasicInfoByUser(PersonalBasicInfo basicInfo) {
        try {
            PersonalBasicInfo info = personalBasicInfoRepository.findPersonalBasicInfoByUser(basicInfo.getUser());
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PersonnelManagementResponseDTO updatePersonalBasicInfo(PersonalBasicInfo basicInfo, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Basic information update successful", true);
        System.out.println(basicInfo);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            basicInfo.setUser(user);
            PersonalBasicInfo info = this.getPersonalBasicInfoByUser(basicInfo);
            boolean success = this.updateBasicInfo(info);
            if (!success) {
                response.setMessage("Basic information update failed");
            }
        } else {
            response.setMessage("User not found");
        }
        return response;
    }

    private boolean updateBasicInfo(PersonalBasicInfo info) {
        try {
            return true;
//            if (Objects.nonNull(info)) {
//                info.setFirstName(basicInfo.getFirstName());
//                info.setLastName(basicInfo.getLastName());
//                info.setDateOfBirth(basicInfo.getDateOfBirth());
//                info.setGender(basicInfo.getGender());
//                personalBasicInfoRepository.save(basicInfo);
//            } else {
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
//        return true;
    }

    public boolean updatePersonalAttributes(PersonalAttributes personalAttributes) {
        try {
            personalAttributesRepository.save(personalAttributes);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updatePersonalFamilyInfo(PersonalFamilyInfo familyInfo) {
        try {
            personalFamilyInfoRepository.save(familyInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updatePersonalIdentificationInfo(PersonalIdentificationInfo identificationInfo) {
        try {
            personalIdentificationInfoRepository.save(identificationInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updatePersonalAddress(PersonalAddressInfo addressInfo) {
        try {
            personalAddressInfoRepository.save(addressInfo);
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
