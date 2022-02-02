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
            PersonalAttributes attributes = PersonalAttributesDTO.getPersonalAttributesEntity(attributesDTO);
            attributes.setUser(user);
            if(this.create(attributes)){
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

    private boolean create(PersonalAttributes attributesInfo) {
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
            PersonalAttributes attributes = PersonalAttributesDTO.getPersonalAttributesEntity(attributesDTO);
            attributes.setUser(user);
            if(this.update(attributes)){
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

    private boolean update(PersonalAttributes attributesInfo) {
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
