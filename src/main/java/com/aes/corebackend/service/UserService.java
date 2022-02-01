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

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserResponseDTO update(User user, long id) {
            User tempUser = userRepository.findById(id).orElse(null);
            //System.out.println("Temp user" + tempUser);
            UserResponseDTO responseDTO = new UserResponseDTO("user not found");

            if(tempUser != null) {
                tempUser.setDesignation(user.getDesignation());
                tempUser.setDepartment(user.getDepartment());
                tempUser.setEmailAddress(user.getEmailAddress());
                tempUser.setBusinessUnit(user.getBusinessUnit());
                tempUser.setEmployeeId(user.getEmployeeId());
                userRepository.save(tempUser);
                responseDTO.setMessage("user updated successfully");
            }

            return responseDTO;

    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}