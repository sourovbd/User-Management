package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
import lombok.Data;

@Data
public class PersonalIdentificationInfoDTO {
    private String nationalID;
    private String etin;// can not create attribute with single lower case first e.g eTin, pTin etc

    public PersonalIdentificationInfo getPersonalIdentificationEntity(PersonalIdentificationInfoDTO identificationInfoDTO) {
        PersonalIdentificationInfo personalIdentificationInfoEntity = new PersonalIdentificationInfo();
        personalIdentificationInfoEntity.setEtin(identificationInfoDTO.getEtin());
        personalIdentificationInfoEntity.setNationalID(identificationInfoDTO.getNationalID());
        return personalIdentificationInfoEntity;
    }
}
