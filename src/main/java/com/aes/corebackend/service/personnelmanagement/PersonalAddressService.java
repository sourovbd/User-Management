package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalAddressInfoRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class PersonalAddressService {
    @Autowired
    PersonalAddressInfoRepository personalAddressInfoRepository;
    @Autowired
    UserService userService;

    public PersonnelManagementResponseDTO createPersonalAddress(PersonalAddressInfoDTO personalAddressInfoDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Personal address creation successful", true, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            //convert basic info DTO to Entity object
            PersonalAddressInfo addressInfoEntity = PersonalAddressInfoDTO.getPersonalAddressInfoEntity(personalAddressInfoDTO);
            addressInfoEntity.setUser(user);
            boolean success = this.createPersonalAddressInfo(addressInfoEntity);
            if (!success) {
                response.setMessage("Personal address creation failed");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }

    private boolean createPersonalAddressInfo(PersonalAddressInfo addressInfo) {
        try {
            personalAddressInfoRepository.save(addressInfo);//create new row into basic info table
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
