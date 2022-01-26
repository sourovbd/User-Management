package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import lombok.Data;

import javax.persistence.Column;

@Data
public class PersonalEducationDTO {
    private String degreeName;
    private String institutionName;
    private float gpaScale;
    private float cgpa;
    private String passingYear;

    public PersonalEducationInfo getPersonalEducationEntity(PersonalEducationDTO educationInfoDTO) {
        PersonalEducationInfo personalEducationInfo = new PersonalEducationInfo();
        personalEducationInfo.setDegreeName(educationInfoDTO.getDegreeName());
        personalEducationInfo.setInstitutionName(educationInfoDTO.getInstitutionName());
        personalEducationInfo.setGpaScale(educationInfoDTO.getGpaScale());
        personalEducationInfo.setCgpa(educationInfoDTO.getCgpa());
        personalEducationInfo.setPassingYear(educationInfoDTO.getPassingYear());
        return personalEducationInfo;
    }
}
