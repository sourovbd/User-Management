package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
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
