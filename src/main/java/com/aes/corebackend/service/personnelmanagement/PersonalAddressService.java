package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
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

    public PersonnelManagementResponseDTO updatePersonalAddress(PersonalAddressInfoDTO updatedPersonalAddressInfoDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Personal address update successful", true, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            //convert basic info DTO to Entity object
            PersonalAddressInfo updatedAddressInfo = PersonalAddressInfoDTO.getPersonalAddressInfoEntity(updatedPersonalAddressInfoDTO);
            updatedAddressInfo.setUser(user);
            //TODO should we fetch existing basic info by user and basic info id both?
            PersonalAddressInfo existingAddressInfo = this.getPersonalAddressInfoByUser(user);
            boolean success = this.updatePersonalAddress(existingAddressInfo, updatedAddressInfo);
            if (!success) {
                response.setMessage("Personal address update failed");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }

    private PersonalAddressInfo getPersonalAddressInfoByUser(User user) {
        try {
            return personalAddressInfoRepository.findPersonalAddressInfoByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean updatePersonalAddress(PersonalAddressInfo existingAddress, PersonalAddressInfo updatedAddress) {
        try {
            if (Objects.nonNull(existingAddress)) {
                existingAddress.setPermanentAddress(updatedAddress.getPermanentAddress());
                existingAddress.setPresentAddress(updatedAddress.getPresentAddress());
                personalAddressInfoRepository.save(existingAddress);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO getPersonalAddressInfo(Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("Basic information not found", false, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalAddressInfo addressInfo = this.getPersonalAddressInfoByUser(user);
            if (Objects.nonNull(addressInfo)) {
                //convert Entity to DTO object and set personal info object
                PersonalAddressInfoDTO addressDTO = PersonalAddressInfoDTO.getPersonalAddressInfoDTO(addressInfo);

                //build response
                response.setMessage("Personal address found");
                response.setSuccess(true);
                response.setData(addressDTO);
            }
        } else {
            response.setMessage("User not found");
            response.setSuccess(false);
        }
        return response;
    }
}
