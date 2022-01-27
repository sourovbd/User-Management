package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalFamilyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyInformationService {
    @Autowired
    PersonalFamilyInfoRepository personalFamilyInfoRepository;

    public boolean createPersonalFamilyInfo(PersonalFamilyInfo familyInfo) {
        try {
            personalFamilyInfoRepository.save(familyInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updatePersonalFamilyInfo(PersonalFamilyInfo familyInfo) {
        try{
            PersonalFamilyInfo dbUpdate = personalFamilyInfoRepository.findPersonalFamilyInfoById(familyInfo.getUser().getId());
            dbUpdate.setFathersName(familyInfo.getFathersName());
            dbUpdate.setMothersName(familyInfo.getMothersName());
            dbUpdate.setMaritalStatus(familyInfo.getMaritalStatus());
            dbUpdate.setSpouseName(familyInfo.getSpouseName());
            personalFamilyInfoRepository.save(dbUpdate);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //GET Request
}
