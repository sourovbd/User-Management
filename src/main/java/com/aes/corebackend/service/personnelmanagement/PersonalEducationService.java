package com.aes.corebackend.service.personnelmanagement;

import com.aes.corebackend.dto.personnelmanagement.PersonalEducationDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalEducationInfo;
import com.aes.corebackend.repository.personnelmanagement.PersonalEducationRepository;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PersonalEducationService {
    @Autowired
    PersonalEducationRepository repository;
    @Autowired
    UserService userService;

    public PersonnelManagementResponseDTO create(PersonalEducationDTO educationDTO, Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalEducationInfo educationInfo = PersonalEducationDTO.getPersonalEducationEntity(educationDTO);
            educationInfo.setUser(user);
            if(create(educationInfo)){
                response.setMessage("Education creation success");
                response.setSuccess(true);
            }else{
                response.setMessage("Education creation failed");
                response.setSuccess(true);
            }
        }
        return response;
    }

    private boolean create(PersonalEducationInfo educationInfo){
        try {
            repository.save(educationInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public PersonnelManagementResponseDTO update(PersonalEducationDTO educationDTO, Long userId, Long educationId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        if (Objects.nonNull(user)) {
            PersonalEducationInfo currentInfo = repository.findPersonalEducationInfoByIdAndUserId(educationId, userId);
            if (Objects.nonNull(currentInfo)){// if a record exists with these id values
                PersonalEducationInfo updateInfo = PersonalEducationDTO.getPersonalEducationEntity(educationDTO);
                updateInfo.setUser(user);
                if (update(updateInfo, educationId)){
                    response.setMessage("Education update success");
                    response.setSuccess(true);
                } else {
                    response.setMessage("Education update fail");
                    response.setSuccess(true);
                }
            } else {
                response.setMessage("Education record not found");
                response.setSuccess(false);
            }
        }
        return response;
    }

    private boolean update(PersonalEducationInfo updateInfo, Long updateId) {
        try {
            updateInfo.setId(updateId);
            repository.save(updateInfo);
        } catch (Exception e){
            return false;
        }
        return true;
    }


    public PersonnelManagementResponseDTO read(Long userId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);

        if(Objects.nonNull(user)){
            ArrayList<PersonalEducationInfo> educationList = repository.findPersonalEducationInfoByUserId(userId);
            if(educationList.size() > 0){
                ArrayList<PersonalEducationDTO> educationDTOs = convertToDTOs(educationList);
                response.setData(educationDTOs);
                response.setSuccess(true);
                response.setMessage("Education Read Success");
            }else {
                response.setMessage("No education records found!");
            }
        }
        return response;
    }

    private ArrayList<PersonalEducationDTO> convertToDTOs(ArrayList<PersonalEducationInfo> educationList) {
        ArrayList<PersonalEducationDTO> educationDTOs = new ArrayList<>();
        educationList.forEach(education -> {
            educationDTOs.add(PersonalEducationDTO.getPersonalEducationDTO(education));
        });
        return educationDTOs;
    }

    public PersonnelManagementResponseDTO read(Long userId, Long educationId) {
        PersonnelManagementResponseDTO response = new PersonnelManagementResponseDTO("User not found", false, null);
        User user = userService.getUserByUserId(userId);
        if(Objects.nonNull(user)){
            PersonalEducationInfo education = repository.findPersonalEducationInfoByIdAndUserId(educationId, userId);
            if(Objects.nonNull(education)){// if a record exists with these id values
                PersonalEducationDTO educationDTO = PersonalEducationDTO.getPersonalEducationDTO(education);
                response.setData(educationDTO);
                response.setMessage("Read Education Success");
            }else{
                response.setMessage("Education Record Not found!");
            }
            response.setSuccess(true);
        }
        return response;
    }
}
