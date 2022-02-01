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
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalTrainingInfo personalTrainingInfoEntity = PersonalTrainingDTO.getPersonalTrainingEntity(trainingDTO);
            personalTrainingInfoEntity.setUser(user);
            boolean success = this.createTraining(personalTrainingInfoEntity);
            if (success) {
                response.setMessage("Training creation success");
                response.setSuccess(true);
            } else {
                response.setMessage("Training creation failed");
                response.setSuccess(false);
            }
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
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalTrainingInfo existingTraining = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(trainingId, userId);
            if (Objects.nonNull(existingTraining)) {
                //convert training DTO to Entity object
                PersonalTrainingInfo updatedTraining = PersonalTrainingDTO.getPersonalTrainingEntity(personalTrainingDTO);
                updatedTraining.setUser(user);
                if (this.updateTraining(updatedTraining, trainingId)) {
                    response.setMessage("Training update successful");
                    response.setSuccess(true);
                } else {
                    response.setMessage("Training update failed");
                    response.setSuccess(false);
                }
            } else {
                response.setMessage("Training record not found");
                response.setSuccess(false);
            }
        }
        return response;
    }

    private boolean updateTraining(PersonalTrainingInfo updatedTraining, Long trainingId) {
        try {
            updatedTraining.setId(trainingId);
            personalTrainingRepository.save(updatedTraining);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO read(Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            ArrayList<PersonalTrainingInfo> trainings = this.getTrainingsByUserId(userId);
            //convert entities into dtos
            if (trainings.size() > 0) {
                //build response
                ArrayList<PersonalTrainingDTO> trainingDTOS = convertToDTOs(trainings);
                response.setData(trainingDTOS);
                response.setSuccess(true);
                response.setMessage("Training read successful");
            } else {
                //build response
                response.setMessage("Training records not found");
            }
        }
        return response;
    }

    private ArrayList<PersonalTrainingDTO> convertToDTOs(ArrayList<PersonalTrainingInfo> trainingList) {
        ArrayList<PersonalTrainingDTO> trainingDTOS = new ArrayList<>();
        trainingList.forEach(training -> {
            trainingDTOS.add(PersonalTrainingDTO.getPersonalTrainingDTO(training));
        });
        return trainingDTOS;
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
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalTrainingInfo training = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(trainingId, userId);
            if (Objects.nonNull(training)) {
                response.setData(PersonalTrainingDTO.getPersonalTrainingDTO(training));
                response.setMessage("Training record found");
                response.setSuccess(true);
            } else {
                response.setMessage("Training record not found");
            }
        }
        return response;
    }
}
