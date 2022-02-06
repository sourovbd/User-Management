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
import static com.aes.corebackend.dto.APIResponse.getResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private static ResponseEntity responseEntity = null;
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    public ResponseEntity<?> create(User user, UserDTO userDto) {

        UserCredential userCredential = new UserCredential();
        userCredential.setEmployeeId(userDto.getEmployeeId());
        userCredential.setRoles(userDto.getRoles());
        userCredential.setActive(true);
        user.setUserCredential(userCredential);

        User createdUser = userRepository.save(user);

        if (Objects.nonNull(createdUser)) {
            emailSender.send(userDto.dtoToEntity(userDto).getEmailAddress(),"This is a test email");
            responseEntity = getResponse(USER_CREATED_SUCCESSFULLY, TRUE, createdUser, HttpStatus.OK);
        } else {
            responseEntity = getResponse(USER_CREATION_FAILED, FALSE, NULL, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public APIResponse update(User user, long id) {
            User existingUser = userRepository.findById(id).orElse(null);
            APIResponse responseDTO = new APIResponse("user not found",false,null);

            if(Objects.nonNull(existingUser)) {

                existingUser.setDesignation(user.getDesignation());
                existingUser.setDepartment(user.getDepartment());
                existingUser.setEmailAddress(user.getEmailAddress());
                existingUser.setBusinessUnit(user.getBusinessUnit());
                existingUser.setEmployeeId(user.getEmployeeId());

                userRepository.save(existingUser);

                responseDTO.setResponses("user updated successfully", true, existingUser);
            }

            return responseDTO;

    }

    public APIResponse read(long id) {
        APIResponse responseDTO = new APIResponse("user not found",false,null);
        User existingUser = userRepository.findById(id).orElse(null);

        if(Objects.nonNull(existingUser)) {
            responseDTO.setResponses("user found", true, existingUser);
        }
        return responseDTO;
    }

    public APIResponse read() {
        APIResponse responseDTO = new APIResponse("No user exist",false,null);
        List<User> existingUsers = userRepository.findAll();

        if(Objects.nonNull(existingUsers)) {
            responseDTO.setResponses("user fetch ok", true, existingUsers);
        }
        return responseDTO;
    }

}