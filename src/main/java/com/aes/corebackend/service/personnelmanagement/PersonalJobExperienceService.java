package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalJobExperienceDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalJobExperience;
import com.aes.corebackend.repository.personnelmanagement.PersonalJobExperienceRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
