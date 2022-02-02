package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
import lombok.Data;

@Data
public class PersonalIdentificationInfoDTO {
    //max length 13
    //min length 10
    //numeric
    private String nationalID;
    //max length - need to check
    //numeric - need to check and confirm
    private String etin;// can not create attribute with single lower case first e.g eTin, pTin etc

    public static PersonalIdentificationInfo getPersonalIdentificationEntity(PersonalIdentificationInfoDTO identificationInfoDTO) {
        PersonalIdentificationInfo personalIdentificationInfoEntity = new PersonalIdentificationInfo();
        personalIdentificationInfoEntity.setEtin(identificationInfoDTO.getEtin());
        personalIdentificationInfoEntity.setNationalID(identificationInfoDTO.getNationalID());
        return personalIdentificationInfoEntity;
    }

    public static PersonalIdentificationInfoDTO getPersonalIdentificationDTO(PersonalIdentificationInfo idInfo) {
        PersonalIdentificationInfoDTO idDTO = new PersonalIdentificationInfoDTO();
        idDTO.setEtin(idInfo.getEtin());
        idDTO.setNationalID(idInfo.getNationalID());
        return idDTO;
    }

    public static PersonalIdentificationInfo updateEntityFromDTO(PersonalIdentificationInfoDTO idDTO, PersonalIdentificationInfo id) {
        id.setEtin(idDTO.getEtin());
        id.setNationalID(idDTO.getNationalID());
        return id;
    }
}
