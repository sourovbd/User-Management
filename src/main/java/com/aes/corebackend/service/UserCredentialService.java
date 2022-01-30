package com.aes.corebackend.service;

import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.repository.UserCredentialRepository;
import com.aes.corebackend.repository.UserRepository;
import com.aes.corebackend.util.Constants;
import com.aes.corebackend.util.UserCredentialUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserCredentialService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    public boolean save(UserCredential userCredential) {
        try {
            //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(UserCredential userCredential, Long id) {
        try {
            //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            UserCredential userCredential1 = userCredentialRepository.getById(id);
            userCredential1.setPassword(userCredential.getPassword());
            userCredentialRepository.save(userCredential1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean verifyPassword(UserCredentialDTO userCredentialDTO) {
        try {
            UserCredential userCredential = userCredentialRepository.findByEmployeeId(userCredentialDTO.getEmployeeId());
            if (userCredential.getPassword().equals(userCredentialDTO.getPassword())) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean generateAndSendTempPass(String email) {
        try {
            //fetch user and credentials
            User user = userRepository.findByEmailId(email);
            UserCredential userCredential = userCredentialRepository.findByEmployeeId(""+user.getEmployeeId());

            //generate dummy password
            String password = UserCredentialUtils.generatePassword(Constants.PASSWORD_MIN_LENGTH);
            userCredential.setPassword(password);

            String messageBody = emailSender.buildEmailText(userCredential);
            emailSender.send(user.getEmailAddress(), messageBody);

            userCredentialRepository.save(userCredential); //encrypt password before save, after authentication is done
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public UserCredential getById(Long id) {
        return userCredentialRepository.findById(id).get();
    }
}
