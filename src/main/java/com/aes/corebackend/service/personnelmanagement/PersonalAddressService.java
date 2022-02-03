package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalAddressInfoRepository;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonalAddressService {

    private final PersonalAddressInfoRepository personalAddressInfoRepository;
    private final UserService userService;

    public PersonnelManagementResponseDTO create(PersonalAddressInfoDTO personalAddressInfoDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** create address record  and build response object */
            if (this.create(personalAddressInfoDTO, user)) {
                response.setMessage("Personal address creation successful");
                response.setSuccess(true);
            } else {
                response.setMessage("Personal address creation failed");
            }
        }
        return response;
    }

    private boolean create(PersonalAddressInfoDTO addressInfoDTO, User user) {
        /** convert basic info DTO to Entity object */
        PersonalAddressInfo addressInfoEntity = PersonalAddressInfoDTO.getPersonalAddressInfoEntity(addressInfoDTO);
        addressInfoEntity.setUser(user);
        try {
            personalAddressInfoRepository.save(addressInfoEntity);
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
            PersonalAddressInfo existingAddressInfo = personalAddressInfoRepository.findPersonalAddressInfoByUserId(userId);
            /** check if address exists */
            if (Objects.nonNull(existingAddressInfo)) {
                /** assign updated data to existing data, execute update address and build response object*/
                if (this.update(updatedPersonalAddressInfoDTO, existingAddressInfo)) {
                    response.setMessage("Personal address update successful");
                    response.setSuccess(true);
                } else {
                    response.setMessage("Personal address update failed");
                }
            } else {
                response.setMessage("Personal address record not found");
            }
        }
        return response;
    }

    private boolean update(PersonalAddressInfoDTO updatedPersonalAddressInfoDTO, PersonalAddressInfo existingAddressInfo) {
        /** convert address info DTO to Entity */
        PersonalAddressInfo updatedAddressInfo = PersonalAddressInfoDTO.updateEntityFromDTO(existingAddressInfo, updatedPersonalAddressInfoDTO);
        try {
            personalAddressInfoRepository.save(updatedAddressInfo);
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
            PersonalAddressInfo addressInfo = personalAddressInfoRepository.findPersonalAddressInfoByUserId(userId);
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
