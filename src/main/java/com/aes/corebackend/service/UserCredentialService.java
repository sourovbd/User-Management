package com.aes.corebackend.service;

import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.UserCredentialDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.exception.ResourceNotFoundException;
import com.aes.corebackend.repository.UserCredentialRepository;
import com.aes.corebackend.repository.UserRepository;
import com.aes.corebackend.util.Constants;
import com.aes.corebackend.util.UserCredentialUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

import static com.aes.corebackend.dto.APIResponse.getResponse;
import static com.aes.corebackend.util.response.APIResponseDesc.*;

@Service
@RequiredArgsConstructor
public class UserCredentialService {

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserCredentialRepository userCredentialRepository;

    private final UserRepository userRepository;

    private final EmailSender emailSender;

    private static ResponseEntity responseEntity = null;

    public ResponseEntity<?> save(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        UserCredential userCredential1 = userCredentialRepository.save(userCredential);

        return Objects.nonNull(userCredential1) ?
                getResponse(USER_CREDENTIAL_CREATED_SUCCESSFULLY, TRUE, userCredential1, HttpStatus.OK) :
                getResponse(USER_CREDENTIAL_CREATION_FAILED, TRUE, NULL, HttpStatus.NO_CONTENT);
    }

    public APIResponse update(UserCredential userCredential) {

        UserCredential existingUserCredential = getEmployeeId(userCredential.getEmployeeId());
        if (Objects.nonNull(existingUserCredential)) {
            existingUserCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        }
        UserCredential updatedUserCredential = userCredentialRepository.save(existingUserCredential);
        return Objects.nonNull(updatedUserCredential) ?
                new APIResponse("Success", true, updatedUserCredential) :
                new APIResponse("Failed", false, null);
    }

    public APIResponse verifyPassword(UserCredentialDTO userCredentialDTO) {
        UserCredential userCredential =  userCredentialRepository.findByEmployeeId(userCredentialDTO.getEmployeeId())
                .orElseThrow(ResourceNotFoundException::new);
        return passwordEncoder.matches(userCredentialDTO.getPassword(), userCredential.getPassword()) ?
                new APIResponse("Valid Password", true, userCredential) :
                new APIResponse("Invalid Password", false, null);
    }

    public UserCredential getEmployeeId(String employeeId) {
        return userCredentialRepository.findByEmployeeId(employeeId).orElseThrow(ResourceNotFoundException::new);
    }

    public APIResponse generateAndSendTempPass(String email) {
        User user = userRepository.findByEmailAddress(email).get();
        if (Objects.nonNull(user)) {
            UserCredential userCredential = user.getUserCredential();

            String password = UserCredentialUtils.generatePassword(Constants.PASSWORD_MIN_LENGTH);
            userCredential.setPassword(password);

            String messageBody = emailSender.buildEmailText(userCredential);
            emailSender.send(user.getEmailAddress(), messageBody);

            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential);
            return new APIResponse("A new password is sent to your email.", true, user);
        }
        else {
            return new APIResponse("Employee not found.", false, null);
        }
    }
}
