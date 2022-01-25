package com.aes.corebackend.dto;

import com.aes.corebackend.entity.PersonalFamilyInfo;
import lombok.Data;

@Data
public class PersonalFamilyInfoDTO {
    private String materialStatus;
    private String fathersName;
    private String mothersName;
    private String spouseName;

    public PersonalFamilyInfo getPersonalFamilyDTOToEntity(PersonalFamilyInfoDTO familyInfoDTO) {
        PersonalFamilyInfo personalFamilyInfoEntity = new PersonalFamilyInfo();
        personalFamilyInfoEntity.setFathersName(familyInfoDTO.getFathersName());
        personalFamilyInfoEntity.setMothersName(familyInfoDTO.getMothersName());
        personalFamilyInfoEntity.setMaterialStatus(familyInfoDTO.getMaterialStatus());
        personalFamilyInfoEntity.setSpouseName(familyInfoDTO.getSpouseName());
        return personalFamilyInfoEntity;
    }
}
