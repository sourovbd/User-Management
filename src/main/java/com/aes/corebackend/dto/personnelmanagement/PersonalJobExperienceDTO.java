package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import lombok.Data;

@Data
public class PersonalJobExperienceDTO {

    private String employerName;
    private String startDate;
    private String endDate;
    private String designation;
    private String responsibilities;

    public PersonalJobExperience getPersonalJobExperienceEntity(PersonalJobExperienceDTO experienceDTO) {
        PersonalJobExperience personalJobExperience = new PersonalJobExperience();
        personalJobExperience.setEmployerName(experienceDTO.getEmployerName());
        personalJobExperience.setStartDate(experienceDTO.getStartDate());
        personalJobExperience.setEndDate(experienceDTO.getEndDate());
        personalJobExperience.setDesignation(experienceDTO.getDesignation());
        personalJobExperience.setResponsibilities(experienceDTO.getResponsibilities());
        return personalJobExperience;
    }
}
