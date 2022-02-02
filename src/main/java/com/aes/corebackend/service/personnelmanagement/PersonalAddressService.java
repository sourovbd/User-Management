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

    public PersonnelManagementResponseDTO create(PersonalAddressInfoDTO personalAddressInfoDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** convert basic info DTO to Entity object */
            PersonalAddressInfo addressInfoEntity = PersonalAddressInfoDTO.getPersonalAddressInfoEntity(personalAddressInfoDTO);
            addressInfoEntity.setUser(user);
            /** create address record  and build response object */
            if (this.createPersonalAddressInfo(addressInfoEntity)) {
                response.setMessage("Personal address creation successful");
                response.setSuccess(true);
            } else {
                response.setMessage("Personal address creation failed");
                response.setSuccess(false);
            }
        }
        return response;
    }

    private boolean createPersonalAddressInfo(PersonalAddressInfo addressInfo) {
        try {
            personalAddressInfoRepository.save(addressInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO update(PersonalAddressInfoDTO updatedPersonalAddressInfoDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalAddressInfo existingAddressInfo = this.getPersonalAddressInfoByUser(user);
            /** check if address exists */
            if (Objects.nonNull(existingAddressInfo)) {
                /** assign updated data to existing data, execute update address and build response object*/
                if (this.updatePersonalAddress(PersonalAddressInfoDTO.assignDTOToEntity(existingAddressInfo, updatedPersonalAddressInfoDTO))) {
                    response.setMessage("Personal address update successful");
                    response.setSuccess(true);
                } else {
                    response.setMessage("Personal address update failed");
                    response.setSuccess(false);
                }
            } else {
                response.setMessage("Personal address record not found");
                response.setSuccess(false);
            }
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

    private boolean updatePersonalAddress(PersonalAddressInfo addressEntity) {
        try {
            personalAddressInfoRepository.save(addressEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public PersonnelManagementResponseDTO read(Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalAddressInfo addressInfo = this.getPersonalAddressInfoByUser(user);
            /** check if address exists */
            if (Objects.nonNull(addressInfo)) {
                /** convert Entity to DTO object and build response object */
                response.setData(PersonalAddressInfoDTO.getPersonalAddressInfoDTO(addressInfo));
                response.setMessage("Personal address record found");
                response.setSuccess(true);
            } else {
                response.setMessage("Personal address record not found");
            }
        }
        return response;
    }
}
