package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import lombok.Data;

import javax.persistence.Column;

@Data
public class PersonalEducationDTO {
    private Long id;
    //max length 30
    //a-zA-Z[.]
    private String degreeName;
    //max length 30
    //a-zA-Z
    private String institutionName;
    //max value 5.0
    private float gpaScale;
    //max value 5.0
    private float cgpa;
    //change into Date type
    //date format
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
