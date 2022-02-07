package com.aes.corebackend.service;

import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.aes.corebackend.util.response.APIResponseDesc.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private static APIResponse apiResponse = null;
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    public APIResponse create(User user, UserDTO userDto) {

        UserCredential userCredential = new UserCredential();
        userCredential.setEmployeeId(userDto.getEmployeeId());
        userCredential.setRoles(userDto.getRoles());
        userCredential.setActive(true);
        user.setUserCredential(userCredential);

        User createdUser = userRepository.save(user);
        if (Objects.nonNull(createdUser)) {
            emailSender.send(userDto.dtoToEntity(userDto).getEmailAddress(),"This is a test email");
            apiResponse.setResponses(USER_CREATED_SUCCESSFULLY, TRUE, createdUser);
        } else {
            apiResponse.setResponses(USER_CREATION_FAILED, FALSE, NULL);
        }
        return apiResponse;
    }

    public APIResponse update(User user, long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        apiResponse.setResponses(USER_UPDATE_FAILED, FALSE, NULL);
        if(Objects.nonNull(existingUser)) {
            existingUser.setDesignation(user.getDesignation());
            existingUser.setDepartment(user.getDepartment());
            existingUser.setEmailAddress(user.getEmailAddress());
            existingUser.setBusinessUnit(user.getBusinessUnit());
            existingUser.setEmployeeId(user.getEmployeeId());

            userRepository.save(existingUser);
            apiResponse.setResponses(USER_UPDATED_SUCCESSFULLY, TRUE, existingUser);
        }
        return apiResponse;
    }

    public APIResponse read(long id) {
        apiResponse.setResponses(USER_NOT_FOUND, FALSE, NULL);
        User existingUser = userRepository.findById(id).orElse(null);
        if(Objects.nonNull(existingUser)) {
            apiResponse.setResponses(USER_FOUND, TRUE, existingUser);
        }
        return apiResponse;
    }

    public APIResponse read() {
        apiResponse.setResponses(NO_USER_EXISTS, FALSE, NULL);
        List<User> existingUsers = userRepository.findAll();
        if(Objects.nonNull(existingUsers)) {
            apiResponse.setResponses(USER_FETCH_OK, TRUE, existingUsers);
        }
        return apiResponse;
    }

}