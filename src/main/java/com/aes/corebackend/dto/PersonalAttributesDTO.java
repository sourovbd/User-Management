package com.aes.corebackend.dto;

import com.aes.corebackend.entity.PersonalAttributes;
import com.aes.corebackend.entity.PersonalBasicInfo;
import lombok.Data;

@Data
public class PersonalAttributesDTO {
    private String religion;
    private String bloodGroup;
    private String birthPlace;
    private String nationality;

    public PersonalAttributes getPersonalAttributesEntity(PersonalAttributesDTO attributesDTO) {
        PersonalAttributes attributesEntity = new PersonalAttributes();
        attributesEntity.setReligion(attributesDTO.getReligion());
        attributesEntity.setBirthPlace(attributesDTO.getBirthPlace());
        attributesEntity.setNationality(attributesDTO.getNationality());
        attributesEntity.setBloodGroup(attributesDTO.getBloodGroup());
        return attributesEntity;
    }
}
