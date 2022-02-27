package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

import com.aes.corebackend.util.DateUtils;

@Data
public class PersonalJobExperienceDTO implements Serializable {
    private Long id;

    @Length(min = 0, max = 50, message = "Employer name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Employer name field cannot have special characters")
    private String employerName;

    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}", message="Invalid start date")
    private String startDate;

    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}", message="Invalid end date")
    private String endDate;

    @Length(min = 0, max = 50, message = "Designation field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Designation field cannot have special characters")
    private String designation;

    @Length(min = 0, max = 255, message = "Responsibilities field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Responsibilities field cannot have numeric or special characters")
    private String responsibilities;

    @AssertTrue(message = "Invalid date range")
    private boolean isValidDateRange() {

        if (Objects.nonNull(startDate) && startDate.length()>0 && Objects.nonNull(endDate) && endDate.length()>0)
            return DateUtils.convertToLocalDate(endDate).compareTo(DateUtils.convertToLocalDate(startDate)) >= 0;
        else return true;
    }

    public static PersonalJobExperience getPersonalJobExperienceEntity(PersonalJobExperienceDTO experienceDTO) {
        PersonalJobExperience personalJobExperience = new PersonalJobExperience();
        personalJobExperience.setId(experienceDTO.getId());
        personalJobExperience.setEmployerName(experienceDTO.getEmployerName());
        personalJobExperience.setStartDate(DateUtils.convertToLocalDate(experienceDTO.getStartDate()));
        personalJobExperience.setEndDate(DateUtils.convertToLocalDate(experienceDTO.getEndDate()));
        personalJobExperience.setDesignation(experienceDTO.getDesignation());
        personalJobExperience.setResponsibilities(experienceDTO.getResponsibilities());
        return personalJobExperience;
    }

    public static PersonalJobExperienceDTO getPersonalJobExperienceDTO(PersonalJobExperience jobExperience) {
        PersonalJobExperienceDTO jobExperienceDTO = new PersonalJobExperienceDTO();
        jobExperienceDTO.setId(jobExperience.getId());
        jobExperienceDTO.setEmployerName(jobExperience.getEmployerName());
        jobExperienceDTO.setStartDate(DateUtils.convertLocalDateToString(jobExperience.getStartDate()));
        jobExperienceDTO.setEndDate(DateUtils.convertLocalDateToString(jobExperience.getEndDate()));
        jobExperienceDTO.setDesignation(jobExperience.getDesignation());
        jobExperienceDTO.setResponsibilities(jobExperience.getResponsibilities());
        return jobExperienceDTO;
    }
}
