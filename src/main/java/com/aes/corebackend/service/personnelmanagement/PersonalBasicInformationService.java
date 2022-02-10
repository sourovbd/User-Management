package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalBasicInfoRepository;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;
import static com.aes.corebackend.util.response.AjaxResponseStatus.ERROR;
import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;

@Service
@RequiredArgsConstructor
public class PersonalBasicInformationService {

    private final PersonalBasicInfoRepository personalBasicInfoRepository;
    private final UserService userService;
    private APIResponse apiResponse = APIResponse.getApiResponse();

    public APIResponse create(PersonalBasicInfoDTO basicInfoDTO, Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            /** create user and build response object */
            if (this.create(basicInfoDTO, user)) {
                apiResponse.setResponse(BASIC_INFORMATION_CREATE_SUCCESS, TRUE, null, SUCCESS);
            } else {
                apiResponse.setMessage(BASIC_INFORMATION_CREATE_FAIL);
            }
        }
        return apiResponse;
    }

    private boolean create(PersonalBasicInfoDTO basicInfoDTO, User user) {
        /** convert basic info DTO to Entity object */
        PersonalBasicInfo basicInfo = PersonalBasicInfoDTO.getPersonalBasicInfoEntity(basicInfoDTO);
        basicInfo.setUser(user);
        try {
            personalBasicInfoRepository.save(basicInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public APIResponse update(PersonalBasicInfoDTO updatedBasicInfoDTO, Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalBasicInfo existingBasicInfo = personalBasicInfoRepository.findPersonalBasicInfoByUserId(userId);
            /** check if basic information record exists */
            if (Objects.nonNull(existingBasicInfo)) {
                /** update record and build response object */
                if (this.update(updatedBasicInfoDTO, existingBasicInfo)) {
                    apiResponse.setResponse(BASIC_INFORMATION_UPDATE_SUCCESS, TRUE, null, SUCCESS);
                } else {
                    apiResponse.setMessage(BASIC_INFORMATION_UPDATE_FAIL);
                }
            } else {
                apiResponse.setMessage(BASIC_INFORMATION_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private boolean update(PersonalBasicInfoDTO basicInfoDTO, PersonalBasicInfo existingBasicInfo) {
        /** convert basic info DTO to Entity object */
        PersonalBasicInfo updatedBasicInfo = PersonalBasicInfoDTO.updateEntityFromDTO(existingBasicInfo, basicInfoDTO);
        try {
            personalBasicInfoRepository.save(updatedBasicInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public APIResponse read(Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** check if user exists */
        if (Objects.nonNull(user)) {
            PersonalBasicInfo basicInfo = personalBasicInfoRepository.findPersonalBasicInfoByUserId(userId);
            /** check if basic information exists */
            if (Objects.nonNull(basicInfo)) {
                /** convert Entity to DTO object and build response object */
                apiResponse.setResponse(BASIC_INFORMATION_RECORD_FOUND, TRUE, PersonalBasicInfoDTO.getPersonalBasicInfoDTO(basicInfo), SUCCESS);
            } else {
                apiResponse.setMessage(BASIC_INFORMATION_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }
}
