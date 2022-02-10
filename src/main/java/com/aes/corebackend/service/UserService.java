package com.aes.corebackend.service;

import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserRepository;
import com.aes.corebackend.util.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.aes.corebackend.util.response.APIResponseMessage.*;
import static com.aes.corebackend.util.response.APIResponse.getApiResponse;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final EmailSender emailSender;

    private APIResponse apiResponse = getApiResponse();

    public APIResponse create(User user, UserDTO userDto) {
        UserCredential userCredential = new UserCredential();
        userCredential.setEmployeeId(userDto.getEmployeeId());
        userCredential.setRoles(userDto.getRoles());
        userCredential.setActive(true);
        user.setUserCredential(userCredential);

        User createdUser = userRepository.save(user);
        if (Objects.nonNull(createdUser)) {
            emailSender.send(userDto.dtoToEntity(userDto).getEmailAddress(),"This is a test email");
            apiResponse.setResponse(USER_CREATED_SUCCESSFULLY, TRUE, createdUser);
        } else {
            apiResponse.setResponse(USER_CREATION_FAILED, FALSE, NULL);
        }
        return apiResponse;
    }

    public APIResponse update(UserDTO userDto, long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        apiResponse.setResponse(USER_UPDATE_FAILED, FALSE, NULL);
        if(Objects.nonNull(existingUser)) {
            existingUser.setDesignation(userDto.getDesignation());
            existingUser.setDepartment(userDto.getDepartment());
            existingUser.setEmailAddress(userDto.getEmailAddress());
            existingUser.setBusinessUnit(userDto.getBusinessUnit());
            existingUser.setEmployeeId(userDto.getEmployeeId());
            existingUser.setRoles(userDto.getRoles());

            userRepository.save(existingUser);
            apiResponse.setResponse(USER_UPDATED_SUCCESSFULLY, TRUE, existingUser);
        }
        return apiResponse;
    }

    public APIResponse read(long id) {
        apiResponse.setResponse(USER_NOT_FOUND, FALSE, NULL);
        User existingUser = userRepository.findById(id).orElse(null);
        if(Objects.nonNull(existingUser)) {
            apiResponse.setResponse(USER_FOUND, TRUE, existingUser);
        }
        return apiResponse;
    }

    public APIResponse read() {
        apiResponse.setResponse(NO_USER_EXISTS, FALSE, NULL);
        List<User> existingUsers = userRepository.findAll();
        if(Objects.nonNull(existingUsers)) {
            apiResponse.setResponse(USER_FETCH_OK, TRUE, existingUsers);
        }
        return apiResponse;
    }

    public User getUserByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }
}