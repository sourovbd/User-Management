package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Data
public class PersonalAttributesDTO {

    @Length(min = 3, max = 20, message = "Religion Field must be within 3 to 20 characters")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Religion Field cannot have numeric or special characters")
    private String religion;

    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Blood Group: Invalid")
    private String bloodGroup;

    @Length(min = 0, max = 255, message = "Birth place: Character Length exceeds of 255")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Birth place: Field cannot have numeric or special characters")
    private String birthPlace;

    /**
     * a-zA-z
     * max length 30
     * */
    @Length(min = 0, max = 30, message = "Nationality: Character Length exceeds of 30")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Nationality: Field cannot have numeric or special characters")
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

    public static PersonalAttributes updateEntityFromDTO(PersonalAttributesDTO attributesDTO, PersonalAttributes currentData) {
        currentData.setReligion(attributesDTO.getReligion());
        currentData.setNationality(attributesDTO.getNationality());
        currentData.setBirthPlace(attributesDTO.getBirthPlace());
        currentData.setBloodGroup(attributesDTO.getBloodGroup());
        return currentData;
    }
}
