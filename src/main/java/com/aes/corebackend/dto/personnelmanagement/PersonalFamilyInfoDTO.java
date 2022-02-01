package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import lombok.Data;

@Data
public class PersonalFamilyInfoDTO {
    //enum
    private String maritalStatus;
    //a-zA-Z
    //max length 50
    private String fathersName;
    //a-zA-Z
    //max length 50
    private String mothersName;
    //a-zA-Z
    //max length 50
    private String spouseName;

    public static PersonalFamilyInfo getPersonalFamilyEntity(PersonalFamilyInfoDTO familyInfoDTO) {
        PersonalFamilyInfo personalFamilyInfoEntity = new PersonalFamilyInfo();
        personalFamilyInfoEntity.setFathersName(familyInfoDTO.getFathersName());
        personalFamilyInfoEntity.setMothersName(familyInfoDTO.getMothersName());
        personalFamilyInfoEntity.setMaritalStatus(familyInfoDTO.getMaritalStatus());
        personalFamilyInfoEntity.setSpouseName(familyInfoDTO.getSpouseName());
        return personalFamilyInfoEntity;
    }

    public static PersonalFamilyInfoDTO getPersonalFamilyDTO(PersonalFamilyInfo familyInfo){
        PersonalFamilyInfoDTO familyInfoDTO = new PersonalFamilyInfoDTO();
        familyInfoDTO.setMaritalStatus(familyInfo.getMaritalStatus());
        familyInfoDTO.setFathersName(familyInfo.getFathersName());
        familyInfoDTO.setMothersName(familyInfo.getMothersName());
        familyInfoDTO.setSpouseName(familyInfo.getSpouseName());
        return familyInfoDTO;
    }
}
