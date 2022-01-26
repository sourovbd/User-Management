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
            System.out.println(e);
            return false;
        }
        return true;
    }
    public Optional<User> findById(long id) {
            return userRepository.findById(id);
    }

}
