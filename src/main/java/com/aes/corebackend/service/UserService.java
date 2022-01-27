package com.aes.corebackend.service;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public boolean save(User user) {
        try{
            userRepository.save(user);
        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean update(User user) {
        try{
            User temp_user = userRepository.findById(user.getId()).orElse(user);
            temp_user.setDesignation(user.getDesignation());
            temp_user.setDepartment(user.getDepartment());
            //temp_user.setEmailAddress(user.getEmailAddress());
            temp_user.setBusinessUnit(user.getBusinessUnit());
            //temp_user.setEmployeeId(user.getEmployeeId());
            //userCredential1.setPassword(userCredential.getPassword());
            userRepository.save(temp_user);
        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Optional<User> findById(long id) {
            return userRepository.findById(id);
    }

}
