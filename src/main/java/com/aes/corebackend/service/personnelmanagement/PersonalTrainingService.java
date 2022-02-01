package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalTrainingRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PersonalTrainingService {
    @Autowired
    UserService userService;

    @Autowired
    PersonalTrainingRepository personalTrainingRepository;

    public PersonnelManagementResponseDTO create(PersonalTrainingDTO trainingDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Training creation successful", true, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            //convert training info DTO to Entity object
            PersonalTrainingInfo personalTrainingInfoEntity = PersonalTrainingDTO.getPersonalTrainingEntity(trainingDTO);
            personalTrainingInfoEntity.setUser(user);
            boolean success = this.createTraining(personalTrainingInfoEntity);
            if (!success) {
                response.setMessage("Training creation failed");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;

    }

    private boolean createTraining(PersonalTrainingInfo trainingInfo) {
        try {
            personalTrainingRepository.save(trainingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO update(PersonalTrainingDTO personalTrainingDTO, Long userId, Long trainingId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Training update successful", true, null);
        User user = userService.getUserByUserId(userId);

        if (Objects.nonNull(user)) {
            //convert training DTO to Entity object
            PersonalTrainingInfo updatedTraining = PersonalTrainingDTO.getPersonalTrainingEntity(personalTrainingDTO);
            updatedTraining.setUser(user);

            PersonalTrainingInfo existingTraining = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(trainingId, userId);
            boolean success = this.updateTraining(existingTraining, updatedTraining);
            if (!success) {
                response.setMessage("Training update failed");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }

    private boolean updateTraining(PersonalTrainingInfo existingTraining, PersonalTrainingInfo updatedTraining) {
        try {
            if (Objects.nonNull(existingTraining)) {
                existingTraining.setProgramName(updatedTraining.getProgramName());
                existingTraining.setTrainingInstitute(updatedTraining.getTrainingInstitute());
                existingTraining.setStartDate(updatedTraining.getStartDate());
                existingTraining.setEndDate(updatedTraining.getEndDate());
                existingTraining.setDescription(updatedTraining.getDescription());
                personalTrainingRepository.save(existingTraining);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO read(Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Trainings found", true, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            ArrayList<PersonalTrainingInfo> trainings = this.getTrainingsByUserId(userId);
            ArrayList<PersonalTrainingDTO> trainingDTOS = new ArrayList<>();

            //convert entities into dtos
            if (trainings.size() > 0) {
                trainings.forEach(training -> {
                    PersonalTrainingDTO dto = new PersonalTrainingDTO();
                    dto.setId(training.getId());
                    dto.setProgramName(training.getProgramName());
                    dto.setTrainingInstitute(training.getTrainingInstitute());
                    dto.setDescription(training.getDescription());
                    dto.setStartDate(training.getStartDate());
                    dto.setEndDate(training.getEndDate());
                    trainingDTOS.add(dto);
                });
                //build response
                response.setData(trainingDTOS);
            } else {
                //build response
                response.setMessage("Training not found");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }

    private ArrayList<PersonalTrainingInfo> getTrainingsByUserId(Long userId) {
        ArrayList<PersonalTrainingInfo> trainings = new ArrayList<>();
        try {
            trainings = personalTrainingRepository.findPersonalTrainingInfosByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainings;
    }

    public PersonnelManagementResponseDTO read(Long userId, Long trainingId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Training found", true, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalTrainingInfo training = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(trainingId, userId);
            //convert job experience entity into job experience dto
            if (Objects.nonNull(training)) {
                PersonalTrainingDTO dto = new PersonalTrainingDTO();
                dto.setId(training.getId());
                dto.setProgramName(training.getProgramName());
                dto.setTrainingInstitute(training.getTrainingInstitute());
                dto.setDescription(training.getDescription());
                dto.setStartDate(training.getStartDate());
                dto.setEndDate(training.getEndDate());
                //build response
                response.setData(dto);
            } else {
                //build response
                response.setMessage("Training not found");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }
}
