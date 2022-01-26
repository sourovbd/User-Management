package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
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
