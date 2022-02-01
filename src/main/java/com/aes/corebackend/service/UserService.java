package com.aes.corebackend.service;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user, long id) {
            User tempUser = userRepository.findById(id).get();

            if(Objects.nonNull(tempUser)) {
                tempUser.setDesignation(user.getDesignation());
                tempUser.setDepartment(user.getDepartment());
                tempUser.setEmailAddress(user.getEmailAddress());
                tempUser.setBusinessUnit(user.getBusinessUnit());
                tempUser.setEmployeeId(user.getEmployeeId());

            }
        return userRepository.save(tempUser);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}