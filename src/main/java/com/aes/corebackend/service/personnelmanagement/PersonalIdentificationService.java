package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonalIdentificationInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalIdentificationInfoRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PersonalIdentificationService {
    @Autowired
    PersonalIdentificationInfoRepository repository;
    @Autowired
    UserService userService;


    public PersonnelManagementResponseDTO create(PersonalIdentificationInfoDTO idDTO, Long userId) {
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        if(Objects.nonNull(user)){
            if(create(idDTO, user)){
                responseDTO.setMessage("Create ID Info Success");
                responseDTO.setSuccess(true);
            }else{
                responseDTO.setMessage("Create ID info Fail");
            }
        }
        return responseDTO;
    }

    private boolean create(PersonalIdentificationInfoDTO idDTO, User user){
        PersonalIdentificationInfo id = PersonalIdentificationInfoDTO.getPersonalIdentificationEntity(idDTO);
        id.setUser(user);
        try {
            repository.save(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public PersonnelManagementResponseDTO update(PersonalIdentificationInfoDTO idDTO, Long userId) {
        //check if user exists
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            //check if record exists
            PersonalIdentificationInfo currentData = repository.findPersonalIdentificationInfoByUserId(userId);
            if(Objects.nonNull(currentData)){
                if(this.update(idDTO, currentData)){
                    responseDTO.setMessage("Update ID Info Success");
                    responseDTO.setSuccess(true);
                }else{
                    responseDTO.setMessage("Update ID info Fail");
                }
            }else{
                responseDTO.setMessage("Identification record not found");
            }
        }
        return responseDTO;
    }

    private boolean update(PersonalIdentificationInfoDTO idDTO, PersonalIdentificationInfo currentData) {
        PersonalIdentificationInfo idInfo = PersonalIdentificationInfoDTO.updateEntityFromDTO(idDTO, currentData);
        try {
            repository.save(idInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Read

    public PersonnelManagementResponseDTO read(Long userId) {
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            //Get data by user
            PersonalIdentificationInfo idInfo = fetchData(userId);
            //If data is NonNull--> respond responstDTO with data
            if(Objects.nonNull(idInfo)){
                PersonalIdentificationInfoDTO idDTO = PersonalIdentificationInfoDTO.getPersonalIdentificationDTO(idInfo);
                responseDTO.setMessage("Identification information found");
                responseDTO.setSuccess(true);
                responseDTO.setData(idDTO);
                return responseDTO;
            }else{
                responseDTO.setMessage("Identification information not found");
                responseDTO.setSuccess(true);
                return responseDTO;
            }
        }else{
            return responseDTO;
        }
    }

    private PersonalIdentificationInfo fetchData(Long userId) {
        try {
            PersonalIdentificationInfo idInfo = repository.findPersonalIdentificationInfoByUserId(userId);
            return idInfo;
        }catch (Exception e){
            return null;
        }
    }

}
