package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalAddressInfoDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAddressInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalAddressInfoRepository;
import com.aes.corebackend.service.UserService;
import com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonalAddressService {

    private final PersonalAddressInfoRepository personalAddressInfoRepository;
    private final UserService userService;
    private APIResponse apiResponse = APIResponse.getApiResponse();

    public APIResponse create(PersonalAddressInfoDTO personalAddressInfoDTO, Long userId) {
        apiResponse.setMessage(PersonnelManagementAPIResponseDescription.USER_NOT_FOUND);
        apiResponse.setSuccess(false);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** create address record  and build response object */
            if (this.create(personalAddressInfoDTO, user)) {
                apiResponse.setMessage(PersonnelManagementAPIResponseDescription.ADDRESS_CREATE_SUCCESS);
                apiResponse.setSuccess(true);
            } else {
                apiResponse.setMessage(PersonnelManagementAPIResponseDescription.ADDRESS_CREATE_FAIL);
            }
        }
        return apiResponse;
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

    public APIResponse update(PersonalAddressInfoDTO updatedPersonalAddressInfoDTO, Long userId) {
        apiResponse.setMessage(PersonnelManagementAPIResponseDescription.USER_NOT_FOUND);
        apiResponse.setSuccess(false);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalAddressInfo existingAddressInfo = personalAddressInfoRepository.findPersonalAddressInfoByUserId(userId);
            /** check if address exists */
            if (Objects.nonNull(existingAddressInfo)) {
                /** assign updated data to existing data, execute update address and build response object*/
                if (this.update(updatedPersonalAddressInfoDTO, existingAddressInfo)) {
                    apiResponse.setMessage(PersonnelManagementAPIResponseDescription.ADDRESS_UPDATE_SUCCESS);
                    apiResponse.setSuccess(true);
                } else {
                    apiResponse.setMessage(PersonnelManagementAPIResponseDescription.ADDRESS_UPDATE_FAIL);
                }
            } else {
                apiResponse.setMessage(PersonnelManagementAPIResponseDescription.ADDRESS_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
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

    public APIResponse read(Long userId) {
        apiResponse.setMessage(PersonnelManagementAPIResponseDescription.USER_NOT_FOUND);
        apiResponse.setSuccess(false);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalAddressInfo addressInfo = personalAddressInfoRepository.findPersonalAddressInfoByUserId(userId);
            /** check if address exists */
            if (Objects.nonNull(addressInfo)) {
                /** convert Entity to DTO object and build response object */
                apiResponse.setData(PersonalAddressInfoDTO.getPersonalAddressInfoDTO(addressInfo));
                apiResponse.setMessage(PersonnelManagementAPIResponseDescription.ADDRESS_RECORD_FOUND);
                apiResponse.setSuccess(true);
            } else {
                apiResponse.setMessage(PersonnelManagementAPIResponseDescription.ADDRESS_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }
}
