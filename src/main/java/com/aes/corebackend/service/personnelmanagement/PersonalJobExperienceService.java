package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import com.aes.corebackend.repository.personnelmanagement.PersonalJobExperienceRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PersonalJobExperienceService {
    @Autowired
    UserService userService;

    @Autowired
    PersonalJobExperienceRepository personalJobExperienceRepository;

    public PersonnelManagementResponseDTO create(PersonalJobExperienceDTO jobExperienceDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Job experience creation successful", true, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            //convert basic info DTO to Entity object
            PersonalJobExperience jobExperienceEntity = PersonalJobExperienceDTO.getPersonalJobExperienceEntity(jobExperienceDTO);
            jobExperienceEntity.setUser(user);
            boolean success = this.createJobExperience(jobExperienceEntity);
            if (!success) {
                response.setMessage("Job experience creation failed");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }

    private boolean createJobExperience(PersonalJobExperience jobExperience) {
        try {
            personalJobExperienceRepository.save(jobExperience);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO update(PersonalJobExperienceDTO jobExperienceDTO, Long userId, Long experienceId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Job experience update successful", true, null);
        User user = userService.getUserByUserId(userId);

        if (Objects.nonNull(user)) {
            //convert job experience DTO to Entity object
            PersonalJobExperience updatedJobExperience = PersonalJobExperienceDTO.getPersonalJobExperienceEntity(jobExperienceDTO);
            updatedJobExperience.setUser(user);

            //TODO should we fetch existing basic info by user and basic info id?
            PersonalJobExperience existingJobExperience = personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(experienceId, userId);
            boolean success = this.updateJobExperience(existingJobExperience, updatedJobExperience);
            if (!success) {
                response.setMessage("Job experience update failed");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }

    private boolean updateJobExperience(PersonalJobExperience existingJobExperience, PersonalJobExperience updatedJobExperience) {
        try {
            if (Objects.nonNull(existingJobExperience)) {
                existingJobExperience.setEmployerName(updatedJobExperience.getEmployerName());
                existingJobExperience.setDesignation(updatedJobExperience.getDesignation());
                existingJobExperience.setStartDate(updatedJobExperience.getStartDate());
                existingJobExperience.setEndDate(updatedJobExperience.getEndDate());
                existingJobExperience.setResponsibilities(updatedJobExperience.getResponsibilities());
                personalJobExperienceRepository.save(existingJobExperience);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO getJobExperiences(Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Job experiences found", true, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            ArrayList<PersonalJobExperience> experiences = this.getJobExperiencesByUserId(userId);
            ArrayList<PersonalJobExperienceDTO> experienceDTOS = new ArrayList<>();

            //convert entities into dtos
            if (experiences.size() > 0) {
                experiences.forEach(experience -> {
                    PersonalJobExperienceDTO dto = new PersonalJobExperienceDTO();
                    dto.setId(experience.getId());
                    dto.setEmployerName(experience.getEmployerName());
                    dto.setDesignation(experience.getDesignation());
                    dto.setStartDate(experience.getStartDate());
                    dto.setEndDate(experience.getEndDate());
                    dto.setResponsibilities(experience.getResponsibilities());
                    experienceDTOS.add(dto);
                });
                //build response
                response.setData(experienceDTOS);
            } else {
                //build response
                response.setMessage("Job experience not found");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }

    private ArrayList<PersonalJobExperience> getJobExperiencesByUserId(Long userId) {
        ArrayList<PersonalJobExperience> experiences = new ArrayList<>();
        try {
             experiences = personalJobExperienceRepository.findPersonalJobExperiencesByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return experiences;
    }

    public PersonnelManagementResponseDTO getJobExperience(Long userId, Long experienceId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Job experience found", true, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalJobExperience experience = personalJobExperienceRepository.findPersonalJobExperienceByIdAndUserId(experienceId, userId);
            //convert job experience entity into job experience dto
            if (Objects.nonNull(experience)) {
                PersonalJobExperienceDTO dto = new PersonalJobExperienceDTO();
                dto.setId(experience.getId());
                dto.setEmployerName(experience.getEmployerName());
                dto.setDesignation(experience.getDesignation());
                dto.setStartDate(experience.getStartDate());
                dto.setEndDate(experience.getEndDate());
                dto.setResponsibilities(experience.getResponsibilities());
                //build response
                response.setData(dto);
            } else {
                //build response
                response.setMessage("Job experience not found");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }
}
