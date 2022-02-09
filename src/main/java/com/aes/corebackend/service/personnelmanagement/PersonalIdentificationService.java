package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalIdentificationInfoDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalIdentificationInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalIdentificationInfoRepository;
import com.aes.corebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.aes.corebackend.util.response.AjaxResponseStatus.ERROR;
import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PersonnelManagementAPIResponseDescription.*;

@Service
@RequiredArgsConstructor
public class PersonalIdentificationService {

    private final PersonalIdentificationInfoRepository repository;
    private final UserService userService;
    private APIResponse apiResponse = APIResponse.getApiResponse();

    public APIResponse create(PersonalIdentificationInfoDTO idDTO, Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            if (create(idDTO, user)) {
                apiResponse.setMessage(IDENTIFICATION_CREATE_SUCCESS);
                apiResponse.setSuccess(TRUE);
                apiResponse.setStatus(SUCCESS);
            } else {
                apiResponse.setMessage(IDENTIFICATION_CREATE_FAIL);
            }
        }
        return apiResponse;
    }

    private boolean create(PersonalIdentificationInfoDTO idDTO, User user) {
        PersonalIdentificationInfo id = PersonalIdentificationInfoDTO.getPersonalIdentificationEntity(idDTO);
        id.setUser(user);
        try {
            repository.save(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public APIResponse update(PersonalIdentificationInfoDTO idDTO, Long userId) {
        /** check if user exists */
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** if exists: convert DTO to entity and call create service */
        if(Objects.nonNull(user)){
            /** check if record exists */
            PersonalIdentificationInfo currentData = repository.findPersonalIdentificationInfoByUserId(userId);
            if (Objects.nonNull(currentData)) {
                if (this.update(idDTO, currentData)) {
                    apiResponse.setMessage(IDENTIFICATION_UPDATE_SUCCESS);
                    apiResponse.setSuccess(TRUE);
                    apiResponse.setStatus(SUCCESS);
                } else {
                    apiResponse.setMessage(IDENTIFICATION_UPDATE_FAIL);
                }
            } else {
                apiResponse.setMessage(IDENTIFICATION_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private boolean update(PersonalIdentificationInfoDTO idDTO, PersonalIdentificationInfo currentData) {
        PersonalIdentificationInfo idInfo = PersonalIdentificationInfoDTO.updateEntityFromDTO(idDTO, currentData);
        try {
            repository.save(idInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public APIResponse read(Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);

        /** if exists: convert DTO to entity and call create service */
        if (Objects.nonNull(user)) {
            /** Get data by user */
            PersonalIdentificationInfo idInfo = fetchData(userId);
            /** If data is NonNull--> respond responstDTO with data */
            if (Objects.nonNull(idInfo)) {
                PersonalIdentificationInfoDTO idDTO = PersonalIdentificationInfoDTO.getPersonalIdentificationDTO(idInfo);
                apiResponse.setMessage(IDENTIFICATION_RECORD_FOUND);
                apiResponse.setSuccess(TRUE);
                apiResponse.setData(idDTO);
                apiResponse.setStatus(SUCCESS);
            } else {
                apiResponse.setMessage(IDENTIFICATION_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private PersonalIdentificationInfo fetchData(Long userId) {
        try {
            PersonalIdentificationInfo idInfo = repository.findPersonalIdentificationInfoByUserId(userId);
            return idInfo;
        } catch (Exception e) {
            return null;
        }
    }

}
