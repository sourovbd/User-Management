package com.aes.corebackend.service;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean update(User user, long id) {
        try{
            User tempUser = userRepository.findById(id).orElse(null);
            //User tempUser = userRepository.findByEmployeeId(user.getEmployeeId());
            //nullpointer check
            if(Objects.nonNull(tempUser)) {
                tempUser.setDesignation(user.getDesignation());
                tempUser.setDepartment(user.getDepartment());
                tempUser.setEmailAddress(user.getEmailAddress());
                tempUser.setBusinessUnit(user.getBusinessUnit());
                tempUser.setEmployeeId(user.getEmployeeId());
                //userCredential1.setPassword(userCredential.getPassword());
                userRepository.save(tempUser);
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

}
