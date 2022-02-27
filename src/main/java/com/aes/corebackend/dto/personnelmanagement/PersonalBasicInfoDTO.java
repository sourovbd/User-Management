package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.enumeration.Gender;
import com.aes.corebackend.util.DateUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;

@Data
public class PersonalBasicInfoDTO {
    private Long id;

    @Length(min = 0, max = 50, message = "First name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "First name field cannot have numeric or special characters")
    private String firstName;

    @Length(min = 0, max = 50, message = "Last name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Last name field cannot have numeric or special characters")
    private String lastName;

    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}", message="Invalid date of birth")
    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public static PersonalBasicInfo getPersonalBasicInfoEntity(PersonalBasicInfoDTO basicInfoDTO) {
        System.out.println("getPersonalBasicInfoEntity");
        System.out.println("gender enum value: " + basicInfoDTO.getGender().name());
        PersonalBasicInfo basicInfoEntity = new PersonalBasicInfo();
        basicInfoEntity.setId(basicInfoDTO.getId());
        basicInfoEntity.setFirstName(basicInfoDTO.getFirstName());
        basicInfoEntity.setLastName(basicInfoDTO.getLastName());
        basicInfoEntity.setGender(basicInfoDTO.getGender());
        basicInfoEntity.setDateOfBirth(DateUtils.convertToLocalDate(basicInfoDTO.getDateOfBirth()));
        return basicInfoEntity;
    }

    public static PersonalBasicInfoDTO getPersonalBasicInfoDTO(PersonalBasicInfo personalBasicInfo) {
        PersonalBasicInfoDTO personalBasicInfoDTO = new PersonalBasicInfoDTO();
        personalBasicInfoDTO.setId(personalBasicInfo.getId());
        personalBasicInfoDTO.setFirstName(personalBasicInfo.getFirstName());
        personalBasicInfoDTO.setLastName(personalBasicInfo.getLastName());
        personalBasicInfoDTO.setDateOfBirth(DateUtils.convertLocalDateToString(personalBasicInfo.getDateOfBirth()));
        personalBasicInfoDTO.setGender(personalBasicInfo.getGender());
        return personalBasicInfoDTO;
    }

    public static PersonalBasicInfo updateEntityFromDTO(PersonalBasicInfo basicInfoEntity, PersonalBasicInfoDTO basicInfoDTO) {
        basicInfoEntity.setFirstName(basicInfoDTO.getFirstName());
        basicInfoEntity.setLastName(basicInfoDTO.getLastName());
        basicInfoEntity.setDateOfBirth(DateUtils.convertToLocalDate(basicInfoDTO.getDateOfBirth()));
        basicInfoEntity.setGender(basicInfoDTO.getGender());
        return basicInfoEntity;
    }
}
