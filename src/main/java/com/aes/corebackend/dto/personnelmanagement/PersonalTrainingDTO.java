package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

import com.aes.corebackend.util.DateUtils;

import java.util.Objects;

@Data
public class PersonalTrainingDTO {
    private Long id;

    @Length(min = 0, max = 50, message = "Program name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Program name field cannot have special characters")
    private String programName;

    @Length(min = 0, max = 50, message = "Training institute name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Training institute name field cannot have special characters")
    private String trainingInstitute;

    @Length(min = 0, max = 255, message = "Description field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Description field cannot have special characters")
    private String description;

    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}", message="Invalid start date")
    private String startDate;

    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}", message="Invalid end date")
    private String endDate;

    @AssertTrue(message = "Invalid date range")
    private boolean isValidDateRange() {

        if (Objects.nonNull(startDate) && startDate.length()>0 && Objects.nonNull(endDate) && endDate.length()>0)
            return DateUtils.convertToLocalDate(endDate).compareTo(DateUtils.convertToLocalDate(startDate)) >= 0;
        else return true;
    }
    public static PersonalTrainingInfo getPersonalTrainingEntity(PersonalTrainingDTO trainingDTO) {
        PersonalTrainingInfo personalTrainingInfo = new PersonalTrainingInfo();
        personalTrainingInfo.setId(trainingDTO.getId());
        personalTrainingInfo.setProgramName(trainingDTO.getProgramName());
        personalTrainingInfo.setTrainingInstitute(trainingDTO.getTrainingInstitute());
        personalTrainingInfo.setDescription(trainingDTO.getDescription());
        personalTrainingInfo.setStartDate(DateUtils.convertToLocalDate(trainingDTO.getStartDate()));
        personalTrainingInfo.setEndDate(DateUtils.convertToLocalDate(trainingDTO.getEndDate()));
        return personalTrainingInfo;
    }

    public static PersonalTrainingDTO getPersonalTrainingDTO(PersonalTrainingInfo trainingInfoEntity) {
        PersonalTrainingDTO personalTrainingDTO = new PersonalTrainingDTO();
        personalTrainingDTO.setId(trainingInfoEntity.getId());
        personalTrainingDTO.setProgramName(trainingInfoEntity.getProgramName());
        personalTrainingDTO.setTrainingInstitute(trainingInfoEntity.getTrainingInstitute());
        personalTrainingDTO.setDescription(trainingInfoEntity.getDescription());
        personalTrainingDTO.setStartDate(DateUtils.convertLocalDateToString(trainingInfoEntity.getStartDate()));
        personalTrainingDTO.setEndDate(DateUtils.convertLocalDateToString(trainingInfoEntity.getEndDate()));
        return personalTrainingDTO;
    }
}
