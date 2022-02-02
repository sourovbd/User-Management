package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalFamilyInfoRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FamilyInformationService {
    @Autowired
    PersonalFamilyInfoRepository personalFamilyInfoRepository;
    @Autowired
    UserService userService;

///     CREATE

    public PersonnelManagementResponseDTO create(PersonalFamilyInfoDTO familyInfoDTO, Long userId) {
        //Check if User Exists
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);

        if(Objects.nonNull(user)){
            //convert DTO to Entity
            PersonalFamilyInfo familyInfo = PersonalFamilyInfoDTO.getPersonalFamilyEntity(familyInfoDTO);
            familyInfo.setUser(user);
            if(this.create(familyInfo)){
                responseDTO.setMessage("Create Family Info Success");
                responseDTO.setSuccess(true);
                return responseDTO;
            }
            responseDTO.setMessage("Create Family Info Fail");
            return responseDTO;
        }else{
            return responseDTO;
        }
    }

    private boolean create(PersonalFamilyInfo familyInfo) {
        try {
            personalFamilyInfoRepository.save(familyInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    ///     UPDATE


    public PersonnelManagementResponseDTO update(PersonalFamilyInfoDTO familyInfoDTO, Long userId) {
        //Check if User Exists
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        if(Objects.nonNull(user)){
            //convert DTO to Entity
            PersonalFamilyInfo familyInfo = PersonalFamilyInfoDTO.getPersonalFamilyEntity(familyInfoDTO);
            familyInfo.setUser(user);
            if(this.update(familyInfo)){
                responseDTO.setMessage("Update Family Info Success");
                responseDTO.setSuccess(true);
                return responseDTO;
            }
            responseDTO.setMessage("Update Family Info Fail");
            return responseDTO;
        }else{
            return responseDTO;
        }
    }

    private boolean update(PersonalFamilyInfo familyInfo) {
        try{
            PersonalFamilyInfo dbUpdate = personalFamilyInfoRepository.findPersonalFamilyInfoById(familyInfo.getUser().getId());
            dbUpdate.setFathersName(familyInfo.getFathersName());
            dbUpdate.setMothersName(familyInfo.getMothersName());
            dbUpdate.setMaritalStatus(familyInfo.getMaritalStatus());
            dbUpdate.setSpouseName(familyInfo.getSpouseName());
            personalFamilyInfoRepository.save(dbUpdate);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    ///// READ

    public PersonnelManagementResponseDTO read(Long userId) {
        PersonnelManagementResponseDTO responseDTO = new PersonnelManagementResponseDTO("User not found!", false, null);
        User user = userService.getUserByUserId(userId);
        //if exists: convert DTO to entity and call create service
        if(Objects.nonNull(user)){
            //Get data by user
            PersonalFamilyInfo familyInfo = fetchData(userId);
            //If data is NonNull--> respond responstDTO with data
            if(Objects.nonNull(familyInfo)){
                PersonalFamilyInfoDTO familyInfoDTO = PersonalFamilyInfoDTO.getPersonalFamilyDTO(familyInfo);
                responseDTO.setMessage("Family Information found");
                responseDTO.setSuccess(true);
                responseDTO.setData(familyInfoDTO);
                return responseDTO;
            }else{
                responseDTO.setMessage("Family Information not found");
                responseDTO.setSuccess(true);
                return responseDTO;
            }
        }else{
            return responseDTO;
        }
    }

    private PersonalFamilyInfo fetchData(Long userId){
        try {
            PersonalFamilyInfo familyInfo = personalFamilyInfoRepository.findPersonalFamilyInfoById(userId);
            return familyInfo;
        }catch (Exception e){
            return null;
        }
    }


}
