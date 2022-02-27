package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

@Data
public class PersonalEducationDTO {
    private Long id;

    @Length(min = 0, max = 50, message = "Degree Name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Degree Name: Field cannot have numeric or special characters")
    private String degreeName;

    @Length(min = 0, max = 50, message = "Institution Name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Institution Name: Field cannot have numeric or special characters")
    private String institutionName;

    @Max(value = 5, message = "Please convert GPA Scale to Maximum 5")
    private float gpaScale;

    @Max(value = 5, message = "Please convert CGPA Scale to Maximum 5")
    @Max(5)
    private float cgpa;

    @Pattern(regexp = "[0-9]{4}", message = "Year format incorrect")
    private String passingYear;

    public static PersonalEducationInfo getPersonalEducationEntity(PersonalEducationDTO educationInfoDTO) {
        PersonalEducationInfo personalEducationInfo = new PersonalEducationInfo();
        personalEducationInfo.setId(educationInfoDTO.getId());
        personalEducationInfo.setDegreeName(educationInfoDTO.getDegreeName());
        personalEducationInfo.setInstitutionName(educationInfoDTO.getInstitutionName());
        personalEducationInfo.setGpaScale(educationInfoDTO.getGpaScale());
        personalEducationInfo.setCgpa(educationInfoDTO.getCgpa());
        personalEducationInfo.setPassingYear(educationInfoDTO.getPassingYear());
        return personalEducationInfo;
    }

    public static PersonalEducationDTO getPersonalEducationDTO(PersonalEducationInfo educationInfo){
        PersonalEducationDTO educationDTO = new PersonalEducationDTO();
        educationDTO.setId(educationInfo.getId());
        educationDTO.setDegreeName(educationInfo.getDegreeName());
        educationDTO.setInstitutionName(educationInfo.getInstitutionName());
        educationDTO.setGpaScale(educationInfo.getGpaScale());
        educationDTO.setCgpa(educationInfo.getCgpa());
        educationDTO.setPassingYear(educationInfo.getPassingYear());
        return educationDTO;
    }
}
