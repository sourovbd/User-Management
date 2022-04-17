package com.sv.corebackend.service.usermanagement;

import com.sv.corebackend.dto.usermanagement.UserCredentialDTO;
import com.sv.corebackend.entity.User;
import com.sv.corebackend.entity.UserCredential;
import com.sv.corebackend.exception.ResourceNotFoundException;
import com.sv.corebackend.repository.UserCredentialRepository;
import com.sv.corebackend.repository.UserRepository;
import com.sv.corebackend.util.Constants;
import com.sv.corebackend.util.response.APIResponse;
import com.sv.corebackend.util.response.APIResponseStatus;
import com.sv.corebackend.util.response.UMAPIResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserCredentialService {

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserCredentialRepository userCredentialRepository;

    private final UserRepository userRepository;

    private final EmailSender emailSender;

    private APIResponse apiResponse = APIResponse.getApiResponse();

    public APIResponse update(UserCredential userCredential) {

        UserCredential existingUserCredential = getEmployee(userCredential.getEmployeeId());
        if (Objects.nonNull(existingUserCredential)) {
            existingUserCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        }
        UserCredential updatedUserCredential = userCredentialRepository.save(existingUserCredential);

        return Objects.nonNull(updatedUserCredential) ?
                apiResponse.setResponse(UMAPIResponseMessage.USER_CREDENTIAL_UPDATED_SUCCESSFULLY, UMAPIResponseMessage.TRUE, updatedUserCredential, APIResponseStatus.SUCCESS) :
                apiResponse.setResponse(UMAPIResponseMessage.USER_CREDENTIAL_UPDATE_FAILED, UMAPIResponseMessage.FALSE, UMAPIResponseMessage.NULL, APIResponseStatus.ERROR);
    }

    public APIResponse verifyPassword(UserCredentialDTO userCredentialDTO) {

        UserCredential userCredential = userCredentialRepository.findByEmployeeId(userCredentialDTO.getEmployeeId())
                .orElseThrow(ResourceNotFoundException::new);
        return passwordEncoder.matches(userCredentialDTO.getPassword(), userCredential.getPassword()) ?
                apiResponse.setResponse(UMAPIResponseMessage.VALID_PASSWORD, UMAPIResponseMessage.TRUE, userCredential, APIResponseStatus.SUCCESS) :
                apiResponse.setResponse(UMAPIResponseMessage.INVALID_PASSWORD, UMAPIResponseMessage.FALSE, UMAPIResponseMessage.NULL, APIResponseStatus.ERROR);
    }

    public UserCredential getEmployee(String employeeId) {
        return userCredentialRepository.findByEmployeeId(employeeId).orElseThrow(ResourceNotFoundException::new);
    }

    public APIResponse generateAndSendTempPass(String email) {
        apiResponse.setResponse(UMAPIResponseMessage.EMPLOYEE_NOT_FOUND, UMAPIResponseMessage.FALSE, UMAPIResponseMessage.NULL, APIResponseStatus.ERROR);
        User user = userRepository.findByEmailAddress(email).orElse(null);
        if (Objects.nonNull(user)) {
            String password = generatePassword(Constants.PASSWORD_MIN_LENGTH);

            UserCredential userCredential = user.getUserCredential();
            userCredential.setPassword(password);

            sendEmail(user, userCredential);

            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            userCredentialRepository.save(userCredential);
            apiResponse.setResponse(UMAPIResponseMessage.NEW_PASSWORD_SENT, UMAPIResponseMessage.TRUE, user, APIResponseStatus.SUCCESS);
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
        //String password = Long.toString(random.nextLong(max - min) + min);
        String password = "asfgsadfsadf";
        return password;
    }
}
