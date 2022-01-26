package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import lombok.Data;

@Data
public class PersonalFamilyInfoDTO {
    private String maritalStatus;
    private String fathersName;
    private String mothersName;
    private String spouseName;

    public PersonalFamilyInfo getPersonalFamilyEntity(PersonalFamilyInfoDTO familyInfoDTO) {
        PersonalFamilyInfo personalFamilyInfoEntity = new PersonalFamilyInfo();
        personalFamilyInfoEntity.setFathersName(familyInfoDTO.getFathersName());
        personalFamilyInfoEntity.setMothersName(familyInfoDTO.getMothersName());
        personalFamilyInfoEntity.setMaterialStatus(familyInfoDTO.getMaritalStatus());
        personalFamilyInfoEntity.setSpouseName(familyInfoDTO.getSpouseName());
        return personalFamilyInfoEntity;
    }
}
