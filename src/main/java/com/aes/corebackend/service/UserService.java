package com.aes.corebackend.service;

import com.aes.corebackend.dto.UserResponseDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public UserResponseDTO update(User user, long id) {
            User existingUser = userRepository.findById(id).orElse(null);
            UserResponseDTO responseDTO = new UserResponseDTO("user not found");

            if(existingUser != null) {
                existingUser.setDesignation(user.getDesignation());
                existingUser.setDepartment(user.getDepartment());
                existingUser.setEmailAddress(user.getEmailAddress());
                existingUser.setBusinessUnit(user.getBusinessUnit());
                existingUser.setEmployeeId(user.getEmployeeId());
                userRepository.save(existingUser);
                responseDTO.setMessage("user updated successfully");
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