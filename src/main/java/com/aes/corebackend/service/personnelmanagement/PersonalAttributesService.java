package com.aes.corebackend.service.personnelmanagement;


import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalAttributesDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalAttributes;
import com.aes.corebackend.repository.personnelmanagement.PersonalAttributesRepository;
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
public class PersonalAttributesService {

    private final PersonalAttributesRepository personalAttributesRepository;

    private final UserService userService;

    private APIResponse apiResponse = getApiResponse();

    public APIResponse create(PersonalAttributesDTO attributesDTO, Long userId) {
        /** check if user exists */
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** if exists: convert DTO to entity and call create service */
        if (Objects.nonNull(user)) {
            if (this.create(attributesDTO, user)) {
                apiResponse.setResponse(ATTRIBUTES_CREATE_SUCCESS, TRUE, null, SUCCESS);
            } else {
                apiResponse.setMessage(ATTRIBUTES_CREATE_FAIL);
            }
        }
        return apiResponse;
    }

    private boolean create(PersonalAttributesDTO attributesDTO, User user) {
        PersonalAttributes attributesInfo = PersonalAttributesDTO.getPersonalAttributesEntity(attributesDTO);
        attributesInfo.setUser(user);
        try {
            personalAttributesRepository.save(attributesInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public APIResponse update(PersonalAttributesDTO attributesDTO, Long userId) {/**  error in update */
        /** check if user exists */
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** if exists: convert DTO to entity and call create service */
        if (Objects.nonNull(user)) {
            /** check if record exists */
            PersonalAttributes currentData = personalAttributesRepository.findPersonalAttributesByUserId(userId);
            if (Objects.nonNull(currentData)) {
                if (this.update(attributesDTO, currentData)) {
                    apiResponse.setResponse(ATTRIBUTES_UPDATE_SUCCESS, TRUE, null, SUCCESS);
                } else {
                    apiResponse.setMessage(ATTRIBUTES_UPDATE_FAIL);
                }
            } else {
                apiResponse.setMessage(ATTRIBUTES_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private boolean update(PersonalAttributesDTO attributesDTO, PersonalAttributes currentData) {
        PersonalAttributes attributesInfo = PersonalAttributesDTO.updateEntityFromDTO(attributesDTO, currentData);
        try {
            personalAttributesRepository.save(attributesInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**  READ */
    public APIResponse read(Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);
        /** if exists: convert DTO to entity and call create service */
        if (Objects.nonNull(user)) {
            /** Get data by user */
            PersonalAttributes attributes = fetchData(userId);
            /** If data is NonNull--> respond responstDTO with data */
            if (Objects.nonNull(attributes)) {
                PersonalAttributesDTO attributesDTO = PersonalAttributesDTO.getPersonalAttributesDTO(attributes);
                apiResponse.setResponse(ATTRIBUTES_RECORD_FOUND, TRUE, attributesDTO, SUCCESS);
            } else {
                apiResponse.setMessage(ATTRIBUTES_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private PersonalAttributes fetchData(Long userId) {
        try {
            PersonalAttributes attributes = personalAttributesRepository.findPersonalAttributesByUserId(userId);
            return attributes;
        } catch (Exception e) {
            return null;
        }
    }

}
