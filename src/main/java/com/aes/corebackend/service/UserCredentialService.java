package com.aes.corebackend.service;

import com.aes.corebackend.dto.ResponseDTO;
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

import java.util.Optional;

@Service
public class UserCredentialService {

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    public ResponseDTO save(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        UserCredential userCredential1 = userCredentialRepository.save(userCredential);
        return userCredential1!=null ?
                new ResponseDTO("Saved Successfully", true, userCredential1) :
                new ResponseDTO("Save Failed", false, null);
    }

    public ResponseDTO update(UserCredential userCredential, String employeeId) {
        UserCredential userCredential1 = userCredentialRepository.findByEmployeeId(employeeId).get();
        if (userCredential1!=null) {
            userCredential1.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            UserCredential userCredential2 = userCredentialRepository.save(userCredential1);
            return new ResponseDTO("Updated Successfully.", true, userCredential2);
        }
        else {
            return new ResponseDTO("Invalid Employee ID.", false, null);
        }
    }

    public ResponseDTO verifyPassword(UserCredentialDTO userCredentialDTO) {
        UserCredential userCredential = userCredentialRepository.findByEmployeeId(userCredentialDTO.getEmployeeId()).get();
        return passwordEncoder.matches(userCredentialDTO.getPassword(), userCredential.getPassword()) == true ?
                new ResponseDTO("Valid Password", true, userCredential) :
                new ResponseDTO("Invalid Password", false, null);
    }

    public ResponseDTO generateAndSendTempPass(String email) {
        User user = userRepository.findByEmailAddress(email).get();
        if (user != null) {
            UserCredential userCredential = user.getUserCredential(); //userCredentialRepository.findByEmployeeId(""+user.getEmployeeId()).get();

            String password = UserCredentialUtils.generatePassword(Constants.PASSWORD_MIN_LENGTH);
            userCredential.setPassword(password);

            String messageBody = emailSender.buildEmailText(userCredential);
            emailSender.send(user.getEmailAddress(), messageBody);

            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential);
            return new ResponseDTO("A new password is sent to your email.", false, user);
        }
        else {
            return new ResponseDTO("Employee not found.", false, null);
        }
    }
}
