package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import lombok.Data;

@Data
public class PersonalJobExperienceDTO {
    private Long id;
    private String employerName;
    private String startDate;
    private String endDate;
    private String designation;
    private String responsibilities;

    public PersonalJobExperience getPersonalJobExperienceEntity(PersonalJobExperienceDTO experienceDTO) {
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
