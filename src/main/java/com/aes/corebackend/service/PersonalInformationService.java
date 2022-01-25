package com.aes.corebackend.service;

import com.aes.corebackend.entity.*;
import com.aes.corebackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean updatePersonalBasicInfo(PersonalBasicInfo basicInfo) {
        try {
            personalBasicInfoRepository.save(basicInfo);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
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
}
