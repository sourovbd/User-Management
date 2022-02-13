package com.aes.corebackend.service.usermanagement;

import com.aes.corebackend.dto.usermanagement.UserCredentialDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.usermanagement.UserCredential;
import com.aes.corebackend.exception.ResourceNotFoundException;
import com.aes.corebackend.repository.usermanagement.UserCredentialRepository;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.aes.corebackend.util.Constants;
import com.aes.corebackend.util.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.Random;

import static com.aes.corebackend.util.response.UMAPIResponseMessage.*;
import static com.aes.corebackend.util.response.APIResponse.getApiResponse;
import static com.aes.corebackend.util.response.APIResponseStatus.ERROR;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class UserCredentialService {

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserCredentialRepository userCredentialRepository;

    private final UserRepository userRepository;

    private final EmailSender emailSender;

    private APIResponse apiResponse = getApiResponse();

    public APIResponse update(UserCredential userCredential) {

        UserCredential existingUserCredential = getEmployee(userCredential.getEmployeeId());
        if (Objects.nonNull(existingUserCredential)) {
            existingUserCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        }
        UserCredential updatedUserCredential = userCredentialRepository.save(existingUserCredential);

        return Objects.nonNull(updatedUserCredential) ?
                apiResponse.setResponse(USER_CREDENTIAL_UPDATED_SUCCESSFULLY, TRUE, updatedUserCredential, SUCCESS) :
                apiResponse.setResponse(USER_CREDENTIAL_UPDATE_FAILED, FALSE, NULL, ERROR);
    }

    public APIResponse verifyPassword(UserCredentialDTO userCredentialDTO) {

        UserCredential userCredential = userCredentialRepository.findByEmployeeId(userCredentialDTO.getEmployeeId())
                .orElseThrow(ResourceNotFoundException::new);
        return passwordEncoder.matches(userCredentialDTO.getPassword(), userCredential.getPassword()) ?
                apiResponse.setResponse(VALID_PASSWORD, TRUE, userCredential, SUCCESS) :
                apiResponse.setResponse(INVALID_PASSWORD, FALSE, NULL, ERROR);
    }

    public UserCredential getEmployee(String employeeId) {
        return userCredentialRepository.findByEmployeeId(employeeId).orElseThrow(ResourceNotFoundException::new);
    }

    public APIResponse generateAndSendTempPass(String email) {
        apiResponse.setResponse(EMPLOYEE_NOT_FOUND, FALSE, NULL,ERROR);
        User user = userRepository.findByEmailAddress(email).orElse(null);
        if (Objects.nonNull(user)) {
            String password = generatePassword(Constants.PASSWORD_MIN_LENGTH);

            UserCredential userCredential = user.getUserCredential();
            userCredential.setPassword(password);

            sendEmail(user, userCredential);

            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential);
            apiResponse.setResponse(NEW_PASSWORD_SENT, TRUE, user, SUCCESS);
        }
        return apiResponse;
    }

    public void sendEmail(User user, UserCredential userCredential) {
        String messageBody = emailSender.buildEmailText(userCredential);
        emailSender.send(user.getEmailAddress(), messageBody);
    }

    public String generatePassword(Integer length) {
        Long min = (long) Math.pow(10, length - 1);
        Long max = (long) Math.pow(10, length) - 1;

        Random random = new Random();
        String password = Long.toString(random.nextLong(max - min) + min);
        return password;
    }
}
