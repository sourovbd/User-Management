package com.aes.corebackend.service;

import com.aes.corebackend.entity.PersonalInformation;
import com.aes.corebackend.repository.PersonalInformationRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationService {
    PersonalInformationRepository personalInformationRepository;
    public boolean updatePersonalInformation(PersonalInformation personalInfo) {
        try {
            personalInformationRepository.save(personalInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
