package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import com.aes.corebackend.repository.personnelmanagement.PersonalJobExperienceRepository;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonalJobExperienceService {

    private final UserService userService;
    private final PersonalJobExperienceRepository personalJobExperienceRepository;

    public PersonnelManagementResponseDTO create(PersonalJobExperienceDTO jobExperienceDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** create job experience record and build response object */
            if (this.createJobExperience(jobExperienceDTO, user)) {
                response.setMessage("Job experience creation successful");
                response.setSuccess(false);
            } else {
                response.setMessage("Job experience creation failed");
            }
        }
        return response;
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

    public PersonnelManagementResponseDTO update(PersonalJobExperienceDTO jobExperienceDTO, Long userId, Long experienceId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalJobExperience existingJobExperience = personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(experienceId, userId);
            /** check if job experience record exists */
            if (Objects.nonNull(existingJobExperience)) {
                /** update record and build response object */
                if (this.updateJobExperience(jobExperienceDTO, user, experienceId)) {
                    response.setMessage("Experience update successful");
                    response.setSuccess(true);
                } else {
                    response.setMessage("Experience update failed");
                }
            } else {
                response.setMessage("Experience record not found");
            }
        }
        return response;
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

    public PersonnelManagementResponseDTO read(Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            ArrayList<PersonalJobExperience> experienceList = personalJobExperienceRepository.findPersonalJobExperiencesByUserId(userId);
            if (experienceList.size() > 0) {
                /** convert Entity to DTO objects */
                ArrayList<PersonalJobExperienceDTO> experienceDTOS = this.convertToDTOs(experienceList);
                /** build response object */
                response.setData(experienceDTOS);
                response.setSuccess(true);
                response.setMessage("Job experience read successful");
            } else {
                response.setMessage("Job experience records not found");
            }
        }
        return response;
    }

    private ArrayList<PersonalJobExperienceDTO> convertToDTOs(ArrayList<PersonalJobExperience> experienceList) {
        ArrayList<PersonalJobExperienceDTO> experienceDTOS = new ArrayList<>();
        experienceList.forEach(experience -> {
            experienceDTOS.add(PersonalJobExperienceDTO.getPersonalJobExperienceDTO(experience));
        });
        return experienceDTOS;
    }

    public PersonnelManagementResponseDTO read(Long userId, Long experienceId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalJobExperience experience = personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(experienceId, userId);
            /** check if job experience record exists */
            if (Objects.nonNull(experience)) {
                /** convert Entity to DTO and build response object */
                response.setData(PersonalJobExperienceDTO.getPersonalJobExperienceDTO(experience));
                response.setMessage("Job experience record found");
                response.setSuccess(true);
            } else {
                response.setMessage("Job experience record not found");
            }
        }
        return response;
    }
}
