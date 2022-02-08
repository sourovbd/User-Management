package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalTrainingRepository;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonalTrainingService {

    private final UserService userService;
    private final PersonalTrainingRepository personalTrainingRepository;

    public PersonnelManagementResponseDTO create(PersonalTrainingDTO trainingDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** create training record  and build response object */
            if (this.createTraining(trainingDTO, user)) {
                response.setMessage("Training creation successful");
                response.setSuccess(true);
            } else {
                response.setMessage("Training creation failed");
            }
        }
        return response;

    }

    private boolean createTraining(PersonalTrainingDTO trainingDTO, User user) {
        /** convert dto to entity */
        PersonalTrainingInfo personalTrainingInfoEntity = PersonalTrainingDTO.getPersonalTrainingEntity(trainingDTO);
        personalTrainingInfoEntity.setUser(user);
        try {
            personalTrainingRepository.save(personalTrainingInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO update(PersonalTrainingDTO personalTrainingDTO, Long userId, Long trainingId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalTrainingInfo existingTraining = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(trainingId, userId);
            /** check if training record exists */
            if (Objects.nonNull(existingTraining)) {
                /** update record and build response object */
                if (this.updateTraining(personalTrainingDTO, user, trainingId)) {
                    response.setMessage("Training update successful");
                    response.setSuccess(true);
                } else {
                    response.setMessage("Training update failed");
                }
            } else {
                response.setMessage("Training record not found");
            }
        }
        return response;
    }

    private boolean updateTraining(PersonalTrainingDTO updatedTrainingDTO, User user, Long trainingId) {
        /** convert training DTO to Entity object */
        PersonalTrainingInfo updatedTraining = PersonalTrainingDTO.getPersonalTrainingEntity(updatedTrainingDTO);
        updatedTraining.setUser(user);
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
        /** check if user exists */
        if (Objects.nonNull(user)) {
            ArrayList<PersonalTrainingInfo> trainingList = personalTrainingRepository.findPersonalTrainingInfosByUserId(userId);
            if (trainingList.size() > 0) {
                /** convert Entity into DTO objects */
                ArrayList<PersonalTrainingDTO> trainingDTOS = this.convertToDTOs(trainingList);
                /** build response object */
                response.setData(trainingDTOS);
                response.setSuccess(true);
                response.setMessage("Training read successful");
            } else {
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

    public PersonnelManagementResponseDTO read(Long userId, Long trainingId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalTrainingInfo training = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(trainingId, userId);
            /** check if training record exists */
            if (Objects.nonNull(training)) {
                /** build response object */
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
