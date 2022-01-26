package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import lombok.Data;

@Data
public class PersonalTrainingDTO {
    private String programName;
    private String trainingInstitute;
    private String description;
    private String startDate;
    private String endDate;

    public PersonalTrainingInfo getPersonalTrainingEntity(PersonalTrainingDTO trainingDTO) {
        PersonalTrainingInfo personalTrainingInfo = new PersonalTrainingInfo();
        personalTrainingInfo.setProgramName(trainingDTO.getProgramName());
        personalTrainingInfo.setTrainingInstitute(trainingDTO.getTrainingInstitute());
        personalTrainingInfo.setDescription(trainingDTO.getDescription());
        personalTrainingInfo.setStartDate(trainingDTO.getStartDate());
        personalTrainingInfo.setEndDate(trainingDTO.getEndDate());
        return personalTrainingInfo;
    }
}
