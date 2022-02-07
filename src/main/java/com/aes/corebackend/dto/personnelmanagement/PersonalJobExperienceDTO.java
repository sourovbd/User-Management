package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import com.aes.corebackend.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Data
public class PersonalJobExperienceDTO implements Serializable {
    private Long id;

    @Length(min = 0, max = 50, message = "Employer name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Employer name field cannot have special characters")
    private String employerName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = Constants.BD_TIMEZONE, pattern = Constants.BD_DATE_FORMAT)
    @Past(message = "Start date must be in the past")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = Constants.BD_TIMEZONE, pattern = Constants.BD_DATE_FORMAT)
    @Past(message = "End date must be in the past")
    private Date endDate;

    @Length(min = 0, max = 50, message = "Designation field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Designation field cannot have special characters")
    private String designation;

    @Length(min = 0, max = 255, message = "Responsibilities field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Responsibilities field cannot have numeric or special characters")
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
