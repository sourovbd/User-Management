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
            PersonalIdentificationInfo id = idDTO.getPersonalIdentificationEntity(idDTO);
            id.setUser(user);
            if(create(id)){
                responseDTO.setMessage("Create ID Info Success");
                responseDTO.setSuccess(true);
                return responseDTO;
            }else{
                responseDTO.setMessage("Create ID info Fail");
                return responseDTO;
            }
        }else{
            return responseDTO;
        }
    }

    private boolean create(PersonalIdentificationInfo id){
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
            PersonalIdentificationInfo idInfo = idDTO.getPersonalIdentificationEntity(idDTO);
            idInfo.setUser(user);
            if(update(idInfo)){
                responseDTO.setMessage("Update ID Info Success");
                responseDTO.setSuccess(true);
                return responseDTO;
            }else{
                responseDTO.setMessage("Update ID info Fail");
                return responseDTO;
            }
        }else{
            return responseDTO;
        }
    }

    private boolean update(PersonalIdentificationInfo idInfo) {
        try {
            PersonalIdentificationInfo dbUpdate = repository.findPersonalIdentificationInfoById(idInfo.getUser().getId());
            dbUpdate.setEtin(idInfo.getEtin());
            dbUpdate.setNationalID(idInfo.getNationalID());
            repository.save(dbUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
