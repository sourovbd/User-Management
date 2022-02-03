package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalBasicInfoRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class PersonalBasicInformationService {
    @Autowired
    PersonalBasicInfoRepository personalBasicInfoRepository;
    @Autowired
    UserService userService;

    public PersonnelManagementResponseDTO create(PersonalBasicInfoDTO basicInfoDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** create user and build response object */
            if (this.create(basicInfoDTO, user)) {
                response.setMessage("Basic information creation successful");
                response.setSuccess(true);
            } else {
                response.setMessage("Basic information creation failed");
            }
        }
        return response;
    }

    private boolean create(PersonalBasicInfoDTO basicInfoDTO, User user) {
        /** convert basic info DTO to Entity object */
        PersonalBasicInfo basicInfo = PersonalBasicInfoDTO.getPersonalBasicInfoEntity(basicInfoDTO);
        basicInfo.setUser(user);
        try {
            personalBasicInfoRepository.save(basicInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO update(PersonalBasicInfoDTO updatedBasicInfoDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalBasicInfo existingBasicInfo = personalBasicInfoRepository.findPersonalBasicInfoByUserId(userId);
            /** check if basic information record exists */
            if (Objects.nonNull(existingBasicInfo)) {
                /** update record and build response object */
                if (this.update(updatedBasicInfoDTO, existingBasicInfo)) {
                    response.setMessage("Basic information update successful");
                    response.setSuccess(true);
                } else {
                    response.setMessage("Basic information update failed");
                }
            } else {
                response.setMessage("Basic information record not found");
            }
        }
        return response;
    }

    private boolean update(PersonalBasicInfoDTO basicInfoDTO, PersonalBasicInfo existingBasicInfo) {
        /** convert basic info DTO to Entity object */
        PersonalBasicInfo updatedBasicInfo = PersonalBasicInfoDTO.updateEntityFromDTO(existingBasicInfo, basicInfoDTO);
        try {
            personalBasicInfoRepository.save(updatedBasicInfo);
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
            PersonalBasicInfo basicInfo = personalBasicInfoRepository.findPersonalBasicInfoByUserId(userId);
            /** check if basic information exists */
            if (Objects.nonNull(basicInfo)) {
                /** convert Entity to DTO object and build response object */
                response.setData(PersonalBasicInfoDTO.getPersonalBasicInfoDTO(basicInfo));
                response.setMessage("Basic information found");
                response.setSuccess(true);
            } else {
                response.setMessage("Basic information not found");
            }
        }
        return response;
    }
}
