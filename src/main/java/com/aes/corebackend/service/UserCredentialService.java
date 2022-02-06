package com.aes.corebackend.service;

import com.aes.corebackend.dto.ResponseDTO;
import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.exception.ResourceNotFoundException;
import com.aes.corebackend.repository.UserCredentialRepository;
import com.aes.corebackend.repository.UserRepository;
import com.aes.corebackend.util.Constants;
import com.aes.corebackend.util.UserCredentialUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialService {

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserCredentialRepository userCredentialRepository;

    private final UserRepository userRepository;

    private final EmailSender emailSender;

    public ResponseDTO save(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        UserCredential userCredential1 = userCredentialRepository.save(userCredential);
        return Objects.nonNull(userCredential1) ?
                new ResponseDTO("Saved Successfully", true, userCredential1) :
                new ResponseDTO("Save Failed", false, null);
    }

    public ResponseDTO update(UserCredential userCredential) {

        UserCredential existingUerCredential = getEmployeeId(userCredential.getEmployeeId());
        if (Objects.nonNull(existingUerCredential)) {
            existingUerCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        }
        UserCredential updatedUserCredential = userCredentialRepository.save(existingUerCredential);
        return Objects.nonNull(updatedUserCredential) ?
                new ResponseDTO("Success", true, updatedUserCredential) :
                new ResponseDTO("Failed", false, null);
    }

    public ResponseDTO verifyPassword(UserCredentialDTO userCredentialDTO) {
        UserCredential userCredential =  userCredentialRepository.findByEmployeeId(userCredentialDTO.getEmployeeId()).get();
        return passwordEncoder.matches(userCredentialDTO.getPassword(), userCredential.getPassword()) ?
                new ResponseDTO("Valid Password", true, userCredential) :
                new ResponseDTO("Invalid Password", false, null);
    }

    public UserCredential getEmployeeId(String employeeId) {
        return userCredentialRepository.findByEmployeeId(employeeId).orElseThrow(ResourceNotFoundException::new);
    }

    public ResponseDTO generateAndSendTempPass(String email) {
        User user = userRepository.findByEmailAddress(email).get();
        if (Objects.nonNull(user)) {
            UserCredential userCredential = user.getUserCredential();

            String password = UserCredentialUtils.generatePassword(Constants.PASSWORD_MIN_LENGTH);
            userCredential.setPassword(password);

            String messageBody = emailSender.buildEmailText(userCredential);
            emailSender.send(user.getEmailAddress(), messageBody);

            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential);
            return new ResponseDTO("A new password is sent to your email.", true, user);
        }
        else {
            return new ResponseDTO("Employee not found.", false, null);
        }
    }
}
