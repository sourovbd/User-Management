package com.aes.corebackend.dto;

import com.aes.corebackend.entity.UserCredential;
import lombok.Data;

@Data
public class UserCredentialResponseDTO {
    private String message;
    private UserCredential userCredential;

    public UserCredentialResponseDTO(String message, UserCredential userCredential1) {
        this.message = message;
        this.userCredential = userCredential1;
    }

    public UserCredentialResponseDTO(String message) {
        this.message = message;
    }
}
