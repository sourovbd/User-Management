package com.aes.corebackend.dto;

import com.aes.corebackend.entity.PersonalBasicInfo;
import lombok.Data;

@Data
public class PersonalBasicInfoDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;

    public PersonalBasicInfo getPersonalBasicInfoDTOToEntity(PersonalBasicInfoDTO basicInfoDTO) {
        PersonalBasicInfo basicInfoEntity = new PersonalBasicInfo();
        basicInfoEntity.setFirstName(basicInfoDTO.getFirstName());
        basicInfoEntity.setLastName(basicInfoDTO.getLastName());
        basicInfoEntity.setGender(basicInfoDTO.getGender());
        basicInfoEntity.setDateOfBirth(basicInfoDTO.getDateOfBirth());
        return basicInfoEntity;
    }
}
