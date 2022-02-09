package com.aes.corebackend.service.personnelmanagement;


import com.aes.corebackend.dto.APIResponse;
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
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;


@Service
public class PersonalAttributesService {
    @Autowired
    PersonalAttributesRepository personalAttributesRepository;
    @Autowired
    UserService userService;


    public APIResponse create(PersonalAttributesDTO attributesDTO, Long userId) {
        //check if user exists
        APIResponse responseDTO = new APIResponse(USER_NOT_FOUND, false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            if(this.create(attributesDTO, user)){
                responseDTO.setMessage(ATTRIBUTES_CREATE_SUCCESS);
                responseDTO.setSuccess(true);

            }else{
                responseDTO.setMessage(ATTRIBUTES_CREATE_FAIL);
            }
        }
        return responseDTO;
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


    public APIResponse update(PersonalAttributesDTO attributesDTO, Long userId) {// error in update
        //check if user exists
        APIResponse responseDTO = new APIResponse(USER_NOT_FOUND, false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            //check if record exists
            PersonalAttributes currentData = personalAttributesRepository.findPersonalAttributesByUserId(userId);
            if(Objects.nonNull(currentData)) {
                if (this.update(attributesDTO, currentData)) {
                    responseDTO.setMessage(ATTRIBUTES_UPDATE_SUCCESS);
                    responseDTO.setSuccess(true);
                } else {
                    responseDTO.setMessage(ATTRIBUTES_UPDATE_FAIL);
                }
            } else {
                responseDTO.setMessage(ATTRIBUTES_RECORD_NOT_FOUND);
            }
        }
            return responseDTO;
    }

    private boolean update(PersonalAttributesDTO attributesDTO, PersonalAttributes currentData) {
        PersonalAttributes attributesInfo = PersonalAttributesDTO.updateEntityFromDTO(attributesDTO, currentData);
        try {
            personalAttributesRepository.save(attributesInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /////  READ

    public APIResponse read(Long userId) {
        APIResponse responseDTO = new APIResponse(USER_NOT_FOUND, false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            //Get data by user
            PersonalAttributes attributes = fetchData(userId);
            //If data is NonNull--> respond responstDTO with data
            if(Objects.nonNull(attributes)){
                PersonalAttributesDTO attributesDTO = PersonalAttributesDTO.getPersonalAttributesDTO(attributes);
                responseDTO.setMessage(ATTRIBUTES_RECORD_FOUND);
                responseDTO.setSuccess(true);
                responseDTO.setData(attributesDTO);
                return responseDTO;
            }else{
                responseDTO.setMessage(ATTRIBUTES_RECORD_NOT_FOUND);
                responseDTO.setSuccess(true);
                return responseDTO;
            }
        }else{
            return responseDTO;
        }
    }

    private PersonalAttributes fetchData(Long userId){
        try {
            PersonalAttributes attributes = personalAttributesRepository.findPersonalAttributesByUserId(userId);
            return attributes;
        }catch (Exception e){
            return null;
        }
    }

}
