package com.aes.corebackend.service.personnelmanagement;


import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.repository.UserRepository;
import com.aes.corebackend.repository.personnelmanagement.PersonalAttributesRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PersonalAttributesService {
    @Autowired
    PersonalAttributesRepository personalAttributesRepository;
    @Autowired
    UserService userService;


    public PersonnelManagementResponseDTO createAttributesInfo(PersonalAttributesDTO attributesDTO, Long userId) {
        //check if user exists
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            PersonalAttributes attributes = attributesDTO.getPersonalAttributesEntity(attributesDTO);
            attributes.setUser(user);
            if(createService(attributes)){
                responseDTO.setMessage("Create Attribute Success");
                responseDTO.setSuccess(true);
                return responseDTO;
            }else{
                responseDTO.setMessage("Create Attribute Fail");
                return responseDTO;
            }
        }else{
            return responseDTO;
        }
    }

    private boolean createService(PersonalAttributes attributesInfo) {
        try {
            personalAttributesRepository.save(attributesInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public PersonnelManagementResponseDTO updateAttributesInfo(PersonalAttributesDTO attributesDTO, Long userId) {
        //check if user exists
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            PersonalAttributes attributes = attributesDTO.getPersonalAttributesEntity(attributesDTO);
            attributes.setUser(user);
            if(updateService(attributes)){
                responseDTO.setMessage("Update Attribute Success");
                responseDTO.setSuccess(true);
                return responseDTO;
            }else{
                responseDTO.setMessage("Update Attribute Fail");
                return responseDTO;
            }
        }else{
            return responseDTO;
        }
    }

    public boolean updateService(PersonalAttributes attributesInfo) {
        try {
            PersonalAttributes dbUpdate = personalAttributesRepository.findPersonalAttributesById(attributesInfo.getUser().getId());
            dbUpdate.setBirthPlace(attributesInfo.getBirthPlace());
            dbUpdate.setNationality(attributesInfo.getNationality());
            dbUpdate.setReligion(attributesInfo.getReligion());
            dbUpdate.setBloodGroup(attributesInfo.getBloodGroup());
            personalAttributesRepository.save(dbUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
