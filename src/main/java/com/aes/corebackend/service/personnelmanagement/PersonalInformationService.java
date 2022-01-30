package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.*;
import com.aes.corebackend.repository.personnelmanagement.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationService {
    @Autowired
    PersonalBasicInfoRepository personalBasicInfoRepository;
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

    public boolean updatePersonalBasicInfo(PersonalBasicInfo basicInfo) {
        try {
            PersonalBasicInfo info = personalBasicInfoRepository.findPersonalBasicInfoByUser(basicInfo.getUser());
            if (info != null) {
                info.setFirstName(basicInfo.getFirstName());
                info.setLastName(basicInfo.getLastName());
                info.setDateOfBirth(basicInfo.getDateOfBirth());
                info.setGender(basicInfo.getGender());
                personalBasicInfoRepository.save(info);//this will update the existing data
            } else {
                personalBasicInfoRepository.save(basicInfo);// this is how to insert into database
            }
        } catch (Exception e) {
            System.out.println(e);
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
