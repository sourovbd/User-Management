package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import lombok.Data;

@Data
public class PersonalBasicInfoDTO {
    private Long id;
    //a-zA-z
    //max length 50
    private String firstName;
    //a-zA-z
    //max length 50
    private String lastName;
    //format of date
    private String dateOfBirth;//change to date field
    //enum
    private String gender;

    public static PersonalBasicInfo getPersonalBasicInfoEntity(PersonalBasicInfoDTO basicInfoDTO) {
        PersonalBasicInfo basicInfoEntity = new PersonalBasicInfo();
        basicInfoEntity.setId(basicInfoDTO.getId());
        basicInfoEntity.setFirstName(basicInfoDTO.getFirstName());
        basicInfoEntity.setLastName(basicInfoDTO.getLastName());
        basicInfoEntity.setGender(basicInfoDTO.getGender());
        basicInfoEntity.setDateOfBirth(basicInfoDTO.getDateOfBirth());
        return basicInfoEntity;
    }

    public static PersonalBasicInfoDTO getPersonalBasicInfoDTO(PersonalBasicInfo personalBasicInfo) {
        PersonalBasicInfoDTO personalBasicInfoDTO = new PersonalBasicInfoDTO();
        personalBasicInfoDTO.setId(personalBasicInfo.getId());
        personalBasicInfoDTO.setFirstName(personalBasicInfo.getFirstName());
        personalBasicInfoDTO.setLastName(personalBasicInfo.getLastName());
        personalBasicInfoDTO.setDateOfBirth(personalBasicInfo.getDateOfBirth());
        personalBasicInfoDTO.setGender(personalBasicInfo.getGender());
        return personalBasicInfoDTO;
    }
}
