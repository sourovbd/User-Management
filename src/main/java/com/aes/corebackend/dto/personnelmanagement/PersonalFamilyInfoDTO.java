package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
public class PersonalFamilyInfoDTO {
    //enum
    @Length(min = 0, max = 50, message = "Marital Status: Field must less than 50 characters")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Marital Status: Field cannot have numeric or special characters")
    private String maritalStatus;
    //a-zA-Z
    //max length 50
    @Length(min = 0, max = 50, message = "Father's name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Father's name: Field cannot have numeric or special characters")
    private String fathersName;
    //a-zA-Z
    //max length 50
    @Length(min = 0, max = 50, message = "Mother's Name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Mother's Name: Field cannot have numeric or special characters")
    private String mothersName;
    //a-zA-Z
    //max length 50
    @Length(min = 0, max = 50, message = "Spouse's Name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Spouse's Name: Field cannot have numeric or special characters")
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

    public static PersonalFamilyInfo updateEntityFromDTO(PersonalFamilyInfoDTO familyDTO, PersonalFamilyInfo familyInfo){
        // is the same
        familyInfo.setFathersName(familyDTO.getFathersName());
        familyInfo.setMothersName(familyDTO.getMothersName());
        familyInfo.setMaritalStatus(familyDTO.getMaritalStatus());
        familyInfo.setSpouseName(familyDTO.getSpouseName());
        return familyInfo;
    }
}
