package com.aes.corebackend.service;

import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
