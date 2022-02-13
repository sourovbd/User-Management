package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalFamilyInfoDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalFamilyInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalFamilyInfoRepository;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.aes.corebackend.util.response.APIResponse.getApiResponse;
import static com.aes.corebackend.util.response.AjaxResponseStatus.ERROR;
import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;

@Service
@RequiredArgsConstructor
public class FamilyInformationService {

    private final PersonalFamilyInfoRepository personalFamilyInfoRepository;

    private final UserService userService;

    private APIResponse apiResponse = getApiResponse();

    /**    CREATE */
    public APIResponse create(PersonalFamilyInfoDTO familyInfoDTO, Long userId) {
        /** Check if User Exists */
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            /** convert DTO to Entity */
            if (this.create(familyInfoDTO, user)) {
                apiResponse.setResponse(FAMILY_CREATE_SUCCESS, TRUE, null, SUCCESS);
            } else {
                apiResponse.setMessage(FAMILY_CREATE_FAIL);
            }
        }
        return apiResponse;
    }

    private boolean create(PersonalFamilyInfoDTO familyInfoDTO, User user) {
        PersonalFamilyInfo familyInfo = PersonalFamilyInfoDTO.getPersonalFamilyEntity(familyInfoDTO);
        familyInfo.setUser(user);
        try {
            personalFamilyInfoRepository.save(familyInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /** UPDATE */
    public APIResponse update(PersonalFamilyInfoDTO familyInfoDTO, Long userId) {
        /** Check if User Exists */
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalFamilyInfo currentData = personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(userId);
            if (Objects.nonNull(currentData)) { /** Check if record exists to update */
                if (update(familyInfoDTO, currentData)) {
                    apiResponse.setResponse(FAMILY_UPDATE_SUCCESS, TRUE, null, SUCCESS);
                } else {
                    apiResponse.setMessage(FAMILY_UPDATE_FAIL);
                }
            } else {
                apiResponse.setMessage(FAMILY_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private boolean update(PersonalFamilyInfoDTO familyInfoDTO, PersonalFamilyInfo currentData) {/** seems like problem in the familyInfoDTO being passed in */
        PersonalFamilyInfo familyInfo = PersonalFamilyInfoDTO.updateEntityFromDTO(familyInfoDTO, currentData);
        try {
            personalFamilyInfoRepository.save(familyInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** READ */
    public APIResponse read(Long userId) { /** also not returning the right value */
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** if exists: convert DTO to entity and call create service */
        if (Objects.nonNull(user)) {
            /** Get data by user */
            PersonalFamilyInfo familyInfo = fetchData(userId);
            /** If data is NonNull--> respond responstDTO with data */
            if (Objects.nonNull(familyInfo)) {
                PersonalFamilyInfoDTO familyInfoDTO = PersonalFamilyInfoDTO.getPersonalFamilyDTO(familyInfo);
                apiResponse.setResponse(FAMILY_RECORD_FOUND, TRUE, familyInfoDTO, SUCCESS);
            } else {
                apiResponse.setMessage(FAMILY_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private PersonalFamilyInfo fetchData(Long userId) {
        try {
            PersonalFamilyInfo familyInfo = personalFamilyInfoRepository.findPersonalFamilyInfoByUserId(userId);
            return familyInfo;
        } catch (Exception e) {
            return null;
        }
    }


}
