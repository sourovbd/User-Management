package com.aes.corebackend.dto.personnelmanagement;

import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import lombok.Data;

@Data
public class PersonalTrainingDTO {
    private Long id;
    private String programName;
    private String trainingInstitute;
    private String description;
    private String startDate;
    private String endDate;

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
