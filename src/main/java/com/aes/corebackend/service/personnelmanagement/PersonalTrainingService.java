package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalTrainingDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalTrainingInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalTrainingRepository;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apiguardian.api.API;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;

@Service
@RequiredArgsConstructor
public class PersonalTrainingService {

    private final UserService userService;
    private final PersonalTrainingRepository personalTrainingRepository;
    private APIResponse apiResponse = APIResponse.getApiResponse();

    public APIResponse create(PersonalTrainingDTO trainingDTO, Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** create training record  and build response object */
            if (this.createTraining(trainingDTO, user)) {
                apiResponse.setMessage(TRAINING_CREATE_SUCCESS);
                apiResponse.setSuccess(TRUE);
            } else {
                apiResponse.setMessage(TRAINING_CREATE_FAIL);
            }
        }
        return apiResponse;

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

    public APIResponse update(PersonalTrainingDTO personalTrainingDTO, Long userId, Long trainingId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalTrainingInfo existingTraining = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(trainingId, userId);
            /** check if training record exists */
            if (Objects.nonNull(existingTraining)) {
                /** update record and build response object */
                if (this.updateTraining(personalTrainingDTO, user, trainingId)) {
                    apiResponse.setMessage(TRAINING_UPDATE_SUCCESS);
                    apiResponse.setSuccess(TRUE);
                } else {
                    apiResponse.setMessage(TRAINING_UPDATE_FAIL);
                }
            } else {
                apiResponse.setMessage(TRAINING_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
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

    public APIResponse read(Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            ArrayList<PersonalTrainingInfo> trainingList = personalTrainingRepository.findPersonalTrainingInfosByUserId(userId);
            if (trainingList.size() > 0) {
                /** convert Entity into DTO objects */
                ArrayList<PersonalTrainingDTO> trainingDTOS = this.convertToDTOs(trainingList);
                /** build response object */
                apiResponse.setData(trainingDTOS);
                apiResponse.setSuccess(TRUE);
                apiResponse.setMessage(TRAINING_RECORD_FOUND); //("Training read successful");
            } else {
                apiResponse.setMessage(TRAINING_RECORDS_FOUND); //("Training records not found");
            }
        }
        return apiResponse;
    }

    private ArrayList<PersonalTrainingDTO> convertToDTOs(ArrayList<PersonalTrainingInfo> trainingList) {
        ArrayList<PersonalTrainingDTO> trainingDTOS = new ArrayList<>();
        trainingList.forEach(training -> {
            trainingDTOS.add(PersonalTrainingDTO.getPersonalTrainingDTO(training));
        });
        return trainingDTOS;
    }

    public APIResponse read(Long userId, Long trainingId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalTrainingInfo training = personalTrainingRepository.findPersonalTrainingInfoByIdAndUserId(trainingId, userId);
            /** check if training record exists */
            if (Objects.nonNull(training)) {
                /** build response object */
                apiResponse.setData(PersonalTrainingDTO.getPersonalTrainingDTO(training));
                apiResponse.setMessage(TRAINING_RECORD_FOUND);
                apiResponse.setSuccess(TRUE);
            } else {
                apiResponse.setMessage(TRAINING_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }
}