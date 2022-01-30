package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.entity.User;
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
    public boolean createService(PersonalFamilyInfo familyInfo) {
        try {
            personalFamilyInfoRepository.save(familyInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String createPersonalFamilyInfo(PersonalFamilyInfoDTO familyInfoDTO, Long userId) {
        //Check if User Exists
        User user = userService.getUserByUserId(userId);

        if(Objects.nonNull(user)){
            //convert DTO to Entity
            PersonalFamilyInfo familyInfo = familyInfoDTO.getPersonalFamilyEntity(familyInfoDTO);
            familyInfo.setUser(user);
            if(createService(familyInfo)){
                return "Create Success";
            }
            return "Create Failed";
        }else{
            return "User not found!";
        }
    }
    ///     UPDATE
    public boolean updateService(PersonalFamilyInfo familyInfo) {
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

    public String updatePersonalFamilyInfo(PersonalFamilyInfoDTO familyInfoDTO, Long userId) {
        //Check if User Exists
        User user = userService.getUserByUserId(userId);

        if(Objects.nonNull(user)){
            //convert DTO to Entity
            PersonalFamilyInfo familyInfo = familyInfoDTO.getPersonalFamilyEntity(familyInfoDTO);
            familyInfo.setUser(user);
            if(updateService(familyInfo)){
                return "Update Success";
            }
            return "Update Failed";
        }else{
            return "User not found!";
        }
    }


    //GET Request
}
