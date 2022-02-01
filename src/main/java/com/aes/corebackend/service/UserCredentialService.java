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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserCredentialService {

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    public boolean save(UserCredential userCredential) {
        try {
            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(UserCredential userCredential, String employeeId) {
        try {
            UserCredential userCredential1 = userCredentialRepository.findByEmployeeId(employeeId).get();
            userCredential1.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean verifyPassword(UserCredentialDTO userCredentialDTO) {
        try {
            UserCredential userCredential = userCredentialRepository.findByEmployeeId(userCredentialDTO.getEmployeeId()).get();
            return passwordEncoder.matches(userCredentialDTO.getPassword(), userCredential.getPassword());
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean generateAndSendTempPass(String email) {
        try {
            User user = userRepository.findByEmailAddress(email).get();
            UserCredential userCredential = userCredentialRepository.findByEmployeeId(""+user.getEmployeeId()).get();

            String password = UserCredentialUtils.generatePassword(Constants.PASSWORD_MIN_LENGTH);
            userCredential.setPassword(password);

            String messageBody = emailSender.buildEmailText(userCredential);
            emailSender.send(user.getEmailAddress(), messageBody);

            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential);
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

    public UserCredential getByEmployeeId(String employeeId) {

        return userCredentialRepository.findByEmployeeId(employeeId).get();
    }
}
