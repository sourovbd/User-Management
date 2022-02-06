package com.aes.corebackend.service;

import com.aes.corebackend.dto.ResponseDTO;
import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailSender emailSender;

    public ResponseDTO create(User user, UserDTO userDto) {
        UserCredential userCredential = new UserCredential();
        userCredential.setEmployeeId(userDto.getEmployeeId());
        userCredential.setRoles(userDto.getRoles());
        userCredential.setActive(true);
        user.setUserCredential(userCredential);
        User createdUser = userRepository.save(user);
        ResponseDTO responseDTO = new ResponseDTO("user creation failed",false,null);
        if(Objects.nonNull(createdUser)) {
            // emailSender.send(userDto.dtoToEntity(userDto).getEmailAddress(),"This is a test email");
            responseDTO.setResponses("user created successfully", true, createdUser);
        }
        return responseDTO;
    }

    public ResponseDTO update(User user, long id) {
            User existingUser = userRepository.findById(id).orElse(null);
            ResponseDTO responseDTO = new ResponseDTO("user not found",false,null);

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

    public ResponseDTO read(long id) {
        ResponseDTO responseDTO = new ResponseDTO("user not found",false,null);
        User existingUser = userRepository.findById(id).orElse(null);

        if(Objects.nonNull(existingUser)) {
            responseDTO.setResponses("user found", true, existingUser);
        }
        return responseDTO;
    }

    public ResponseDTO read() {
        ResponseDTO responseDTO = new ResponseDTO("No user exist",false,null);
        List<User> existingUsers = userRepository.findAll();

        if(Objects.nonNull(existingUsers)) {
            responseDTO.setResponses("user fetch ok", true, existingUsers);
        }
        return responseDTO;
    }

}