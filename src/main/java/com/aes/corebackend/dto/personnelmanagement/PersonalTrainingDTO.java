package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import com.aes.corebackend.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = Constants.BD_TIMEZONE, pattern = Constants.BD_DATE_FORMAT)
    @Past(message = "Start date must be in the past")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = Constants.BD_TIMEZONE, pattern = Constants.BD_DATE_FORMAT)
    @Past(message = "End date must be in the past")
    private Date endDate;

    public static PersonalTrainingInfo getPersonalTrainingEntity(PersonalTrainingDTO trainingDTO) {
        PersonalTrainingInfo personalTrainingInfo = new PersonalTrainingInfo();
        personalTrainingInfo.setId(trainingDTO.getId());
        personalTrainingInfo.setProgramName(trainingDTO.getProgramName());
        personalTrainingInfo.setTrainingInstitute(trainingDTO.getTrainingInstitute());
        personalTrainingInfo.setDescription(trainingDTO.getDescription());
        personalTrainingInfo.setStartDate(trainingDTO.getStartDate());
        personalTrainingInfo.setEndDate(trainingDTO.getEndDate());
        return personalTrainingInfo;
    }

    public static PersonalTrainingDTO getPersonalTrainingDTO(PersonalTrainingInfo trainingInfoEntity) {
        PersonalTrainingDTO personalTrainingDTO = new PersonalTrainingDTO();
        personalTrainingDTO.setId(trainingInfoEntity.getId());
        personalTrainingDTO.setProgramName(trainingInfoEntity.getProgramName());
        personalTrainingDTO.setTrainingInstitute(trainingInfoEntity.getTrainingInstitute());
        personalTrainingDTO.setDescription(trainingInfoEntity.getDescription());
        personalTrainingDTO.setStartDate(trainingInfoEntity.getStartDate());
        personalTrainingDTO.setEndDate(trainingInfoEntity.getEndDate());
        return personalTrainingDTO;
    }
}
