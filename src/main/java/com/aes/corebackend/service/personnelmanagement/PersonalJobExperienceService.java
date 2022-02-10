package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import com.aes.corebackend.repository.personnelmanagement.PersonalJobExperienceRepository;
import com.aes.corebackend.service.usermanagement.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;
import static com.aes.corebackend.util.response.APIResponseStatus.ERROR;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class PersonalJobExperienceService {

    private final UserService userService;
    private final PersonalJobExperienceRepository personalJobExperienceRepository;
    private APIResponse apiResponse = APIResponse.getApiResponse();

    public APIResponse create(PersonalJobExperienceDTO jobExperienceDTO, Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** create job experience record and build response object */
            if (this.createJobExperience(jobExperienceDTO, user)) {
                apiResponse.setResponse(JOB_EXPERIENCE_CREATE_SUCCESS, TRUE, null, SUCCESS);
            } else {
                apiResponse.setMessage(JOB_EXPERIENCE_CREATE_FAIL);
            }
        }
        return apiResponse;
    }

    private boolean createJobExperience(PersonalJobExperienceDTO jobExperienceDTO, User user) {
        /** convert DTO to Entity object */
        PersonalJobExperience jobExperienceEntity = PersonalJobExperienceDTO.getPersonalJobExperienceEntity(jobExperienceDTO);
        jobExperienceEntity.setUser(user);
        try {
            personalJobExperienceRepository.save(jobExperienceEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public APIResponse update(PersonalJobExperienceDTO jobExperienceDTO, Long userId, Long experienceId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalJobExperience existingJobExperience = personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(experienceId, userId);
            /** check if job experience record exists */
            if (Objects.nonNull(existingJobExperience)) {
                /** update record and build response object */
                if (this.updateJobExperience(jobExperienceDTO, user, experienceId)) {
                    apiResponse.setResponse(JOB_EXPERIENCE_UPDATE_SUCCESS, TRUE, null, SUCCESS);
                } else {
                    apiResponse.setMessage(JOB_EXPERIENCE_UPDATE_FAIL);
                }
            } else {
                apiResponse.setMessage(JOB_EXPERIENCE_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private boolean updateJobExperience(PersonalJobExperienceDTO jobExperienceDTO, User user, Long experienceId) {
        /** convert experience DTO to Entity object */
        PersonalJobExperience updatedJobExperience = PersonalJobExperienceDTO.getPersonalJobExperienceEntity(jobExperienceDTO);
        updatedJobExperience.setUser(user);
        try {
            updatedJobExperience.setId(experienceId);
            personalJobExperienceRepository.save(updatedJobExperience);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public APIResponse read(Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            ArrayList<PersonalJobExperience> experienceList = personalJobExperienceRepository.findPersonalJobExperiencesByUserId(userId);
            if (experienceList.size() > 0) {
                /** convert Entity to DTO objects */
                ArrayList<PersonalJobExperienceDTO> experienceDTOS = this.convertToDTOs(experienceList);
                /** build response object */
                apiResponse.setResponse(JOB_EXPERIENCE_RECORD_FOUND, TRUE, experienceDTOS, SUCCESS);
            } else {
                apiResponse.setMessage(JOB_EXPERIENCE_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private ArrayList<PersonalJobExperienceDTO> convertToDTOs(ArrayList<PersonalJobExperience> experienceList) {
        ArrayList<PersonalJobExperienceDTO> experienceDTOS = new ArrayList<>();
        experienceList.forEach(experience -> {
            experienceDTOS.add(PersonalJobExperienceDTO.getPersonalJobExperienceDTO(experience));
        });
        return experienceDTOS;
    }

    public APIResponse read(Long userId, Long experienceId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalJobExperience experience = personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(experienceId, userId);
            /** check if job experience record exists */
            if (Objects.nonNull(experience)) {
                /** convert Entity to DTO and build response object */
                apiResponse.setResponse(JOB_EXPERIENCE_RECORD_FOUND, TRUE, PersonalJobExperienceDTO.getPersonalJobExperienceDTO(experience), SUCCESS);
            } else {
                apiResponse.setMessage(JOB_EXPERIENCE_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }
}
