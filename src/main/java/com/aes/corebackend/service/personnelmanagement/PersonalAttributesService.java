package com.aes.corebackend.service.personnelmanagement;


import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.repository.personnelmanagement.PersonalAttributesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalAttributesService {
    @Autowired
    PersonalAttributesRepository personalAttributesRepository;

    public boolean createAttributesInfo(PersonalAttributes attributesInfo) {
        try {
            personalAttributesRepository.save(attributesInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateAttributesInfo(PersonalAttributes attributesInfo) {
        try {
            PersonalAttributes dbUpdate = personalAttributesRepository.findPersonalAttributesById(attributesInfo.getUser().getId());
            dbUpdate.setBirthPlace(attributesInfo.getBirthPlace());
            dbUpdate.setNationality(attributesInfo.getNationality());
            dbUpdate.setReligion(attributesInfo.getReligion());
            dbUpdate.setBloodGroup(attributesInfo.getBloodGroup());
            personalAttributesRepository.save(dbUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
