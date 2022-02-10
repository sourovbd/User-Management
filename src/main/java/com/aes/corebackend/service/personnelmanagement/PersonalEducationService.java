package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.util.response.APIResponse;
import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalEducationRepository;
import com.aes.corebackend.service.usermanagement.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

import static com.aes.corebackend.util.response.APIResponseStatus.ERROR;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.PMAPIResponseMessage.*;

@Service
@RequiredArgsConstructor
public class PersonalEducationService {

    private final PersonalEducationRepository repository;

    private final UserService userService;

    private APIResponse apiResponse = APIResponse.getApiResponse();

    public APIResponse create(PersonalEducationDTO educationDTO, Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);

        if (Objects.nonNull(user)) {
            if (create(educationDTO, user)) {
                apiResponse.setMessage(EDUCATION_CREATE_SUCCESS);
                apiResponse.setSuccess(TRUE);
                apiResponse.setStatus(SUCCESS);
            } else {
                apiResponse.setMessage(EDUCATION_CREATE_FAIL);
            }
        }
        return apiResponse;
    }

    private boolean create(PersonalEducationDTO educationDTO,  User user){
        PersonalEducationInfo educationInfo = PersonalEducationDTO.getPersonalEducationEntity(educationDTO);
        educationInfo.setUser(user);
        try {
            repository.save(educationInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public APIResponse update(PersonalEducationDTO educationDTO, Long userId, Long educationId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);

        if (Objects.nonNull(user)) {
            PersonalEducationInfo currentInfo = repository.findPersonalEducationInfoByIdAndUserId(educationId, userId);
            if (Objects.nonNull(currentInfo)) { /** if a record exists with these id values */
                if (update(educationDTO, user, educationId)) {
                    apiResponse.setMessage(EDUCATION_UPDATE_SUCCESS);
                    apiResponse.setSuccess(TRUE);
                    apiResponse.setStatus(SUCCESS);
                } else {
                    apiResponse.setMessage(EDUCATION_UPDATE_FAIL);
                }
            } else {
                apiResponse.setMessage(EDUCATION_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private boolean update(PersonalEducationDTO educationDTO, User user, Long updateId) {
        PersonalEducationInfo updateInfo = PersonalEducationDTO.getPersonalEducationEntity(educationDTO);
        updateInfo.setUser(user);
        try {
            updateInfo.setId(updateId);
            repository.save(updateInfo);
        } catch (Exception e){
            return false;
        }
        return true;
    }


    public APIResponse read(Long userId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);

        if (Objects.nonNull(user)) {
            ArrayList<PersonalEducationInfo> educationList = repository.findPersonalEducationInfoByUserId(userId);
            if (educationList.size() > 0) {
                ArrayList<PersonalEducationDTO> educationDTOs = convertToDTOs(educationList);
                apiResponse.setData(educationDTOs);
                apiResponse.setSuccess(TRUE);
                apiResponse.setMessage(EDUCATION_RECORDS_FOUND);
                apiResponse.setStatus(SUCCESS);
            } else {
                apiResponse.setMessage(EDUCATION_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }

    private ArrayList<PersonalEducationDTO> convertToDTOs(ArrayList<PersonalEducationInfo> educationList) {
        ArrayList<PersonalEducationDTO> educationDTOs = new ArrayList<>();
        educationList.forEach(education -> {
            educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(education));
        });
        return educationDTOs;
    }

    public APIResponse read(Long userId, Long educationId) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, null, ERROR);
        User user = userService.getUserByUserId(userId);

        if (Objects.nonNull(user)) {
            PersonalEducationInfo education = repository.findPersonalEducationInfoByIdAndUserId(educationId, userId);
            if (Objects.nonNull(education)) { /** if a record exists with these id values */
                PersonalEducationDTO educationDTO = PersonalEducationDTO.getPersonalEducationDTO(education);
                apiResponse.setData(educationDTO);
                apiResponse.setMessage(EDUCATION_RECORD_FOUND);
                apiResponse.setSuccess(TRUE);
                apiResponse.setStatus(SUCCESS);
            } else {
                apiResponse.setMessage(EDUCATION_RECORD_NOT_FOUND);
            }
        }
        return apiResponse;
    }
}