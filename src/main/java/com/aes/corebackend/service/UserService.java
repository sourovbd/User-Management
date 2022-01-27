package com.aes.corebackend.service;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public boolean createUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public User getUserByUserId(Long userId) {
        User user = null;
        try {
            user = userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            System.out.println("Exception in getUserByUserId");
            System.out.println(e);
        }
        return user;
    }
}
