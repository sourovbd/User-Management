package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import lombok.Data;

@Data
public class PersonalJobExperienceDTO {
    private Long id;
    //max length 50
    //alphanumeric
    private String employerName;
    //change into Date type
    //date format
    private String startDate;
    //change into Date type
    //date format
    private String endDate;
    //max length 50
    //alphabets
    private String designation;
    //max length 255
    //alphanumeric + [,.]
    private String responsibilities;

    public static PersonalJobExperience getPersonalJobExperienceEntity(PersonalJobExperienceDTO experienceDTO) {
        PersonalJobExperience personalJobExperience = new PersonalJobExperience();
        personalJobExperience.setId(experienceDTO.getId());
        personalJobExperience.setEmployerName(experienceDTO.getEmployerName());
        personalJobExperience.setStartDate(experienceDTO.getStartDate());
        personalJobExperience.setEndDate(experienceDTO.getEndDate());
        personalJobExperience.setDesignation(experienceDTO.getDesignation());
        personalJobExperience.setResponsibilities(experienceDTO.getResponsibilities());
        return personalJobExperience;
    }

    public static PersonalJobExperienceDTO getPersonalJobExperienceDTO(PersonalJobExperience jobExperience) {
        PersonalJobExperienceDTO jobExperienceDTO = new PersonalJobExperienceDTO();
        jobExperienceDTO.setId(jobExperience.getId());
        jobExperienceDTO.setEmployerName(jobExperience.getEmployerName());
        jobExperienceDTO.setStartDate(jobExperience.getStartDate());
        jobExperienceDTO.setEndDate(jobExperience.getEndDate());
        jobExperienceDTO.setDesignation(jobExperience.getDesignation());
        jobExperienceDTO.setResponsibilities(jobExperience.getResponsibilities());
        return jobExperienceDTO;
    }
}
