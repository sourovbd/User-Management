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


    public PersonnelManagementResponseDTO create(PersonalAttributesDTO attributesDTO, Long userId) {
        //check if user exists
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            if(this.create(attributesDTO, user)){
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

    private boolean create(PersonalAttributesDTO attributesDTO, User user) {
        PersonalAttributes attributesInfo = PersonalAttributesDTO.getPersonalAttributesEntity(attributesDTO);
        attributesInfo.setUser(user);
        try {
            personalAttributesRepository.save(attributesInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public PersonnelManagementResponseDTO update(PersonalAttributesDTO attributesDTO, Long userId) {
        //check if user exists
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            //check if record exists
            PersonalAttributes currentData = personalAttributesRepository.findPersonalAttributesById(userId);
            if(Objects.nonNull(currentData)) {
                if (this.update(attributesDTO, user)) {
                    responseDTO.setMessage("Update Attribute Success");
                    responseDTO.setSuccess(true);
                } else {
                    responseDTO.setMessage("Update Attribute Fail");
                }
            } else {
                responseDTO.setMessage("Attribute Record not found");
            }
        }
            return responseDTO;
    }

    private boolean update(PersonalAttributesDTO attributesDTO, User user) {
        PersonalAttributes attributesInfo = PersonalAttributesDTO.getPersonalAttributesEntity(attributesDTO);
        attributesInfo.setUser(user);
        try {
            attributesInfo.setId(attributesInfo.getUser().getId());
            personalAttributesRepository.save(attributesInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /////  READ

    public PersonnelManagementResponseDTO read(Long userId) {
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            //Get data by user
            PersonalAttributes attributes = fetchData(userId);
            //If data is NonNull--> respond responstDTO with data
            if(Objects.nonNull(attributes)){
                PersonalAttributesDTO attributesDTO = PersonalAttributesDTO.getPersonalAttributesDTO(attributes);
                responseDTO.setMessage("Personal Attribute found");
                responseDTO.setSuccess(true);
                responseDTO.setData(attributesDTO);
                return responseDTO;
            }else{
                responseDTO.setMessage("Personal Attribute not found");
                responseDTO.setSuccess(true);
                return responseDTO;
            }
        }else{
            return responseDTO;
        }
    }

    private PersonalAttributes fetchData(Long userId){
        try {
            PersonalAttributes attributes = personalAttributesRepository.findPersonalAttributesById(userId);
            return attributes;
        }catch (Exception e){
            return null;
        }
    }

}
