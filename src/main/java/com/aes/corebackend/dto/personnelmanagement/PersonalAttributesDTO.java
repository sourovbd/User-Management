package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import lombok.Data;

@Data
public class PersonalAttributesDTO {
    //a-zA-z
    private String religion;
    //Characters- 3
    private String bloodGroup;
    //a-zA-z
    private String birthPlace;
    //a-zA-z
    private String nationality;

    public static PersonalAttributes getPersonalAttributesEntity(PersonalAttributesDTO attributesDTO) {
        PersonalAttributes attributesEntity = new PersonalAttributes();
        attributesEntity.setReligion(attributesDTO.getReligion());
        attributesEntity.setBirthPlace(attributesDTO.getBirthPlace());
        attributesEntity.setNationality(attributesDTO.getNationality());
        attributesEntity.setBloodGroup(attributesDTO.getBloodGroup());
        return attributesEntity;
    }

    public static PersonalAttributesDTO getPersonalAttributesDTO(PersonalAttributes attributes){
        PersonalAttributesDTO attributesDTO = new PersonalAttributesDTO();

        attributesDTO.setReligion(attributes.getReligion());
        attributesDTO.setBloodGroup(attributes.getBloodGroup());
        attributesDTO.setNationality(attributes.getNationality());
        attributesDTO.setBirthPlace(attributes.getBirthPlace());

        return attributesDTO;
    }
}
