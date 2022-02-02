package com.aes.corebackend.service;

import com.aes.corebackend.dto.ResponseDTO;
import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.dto.UserResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
            emailSender.send(userDto.dtoToEntity(userDto).getEmailAddress(),"This is a test email");
            responseDTO.setMessage("user created successfully");
            responseDTO.setSuccess(true);
            responseDTO.setData(null);
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
                responseDTO.setMessage("user updated successfully");
                responseDTO.setSuccess(true);
                responseDTO.setData(null);
            }

            return responseDTO;

    }

    public User read(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> read() {
        return userRepository.findAll();
    }

}