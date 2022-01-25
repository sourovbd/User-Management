package com.aes.corebackend.dto;

import com.aes.corebackend.entity.PersonalIdentificationInfo;
import lombok.Data;

@Data
public class PersonalIdentificationInfoDTO {
    private String nationalID;
    private String eTin;

    public PersonalIdentificationInfo getPersonalIdentificationEntity(PersonalIdentificationInfoDTO identificationInfoDTO) {
        PersonalIdentificationInfo personalIdentificationInfoEntity = new PersonalIdentificationInfo();
        personalIdentificationInfoEntity.setETin(identificationInfoDTO.getETin());
        personalIdentificationInfoEntity.setNationalID(identificationInfoDTO.getNationalID());
        return personalIdentificationInfoEntity;
    }
}
