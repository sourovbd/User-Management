package com.aes.corebackend.dto;

import com.aes.corebackend.entity.UserCredential;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UserCredentialResponseDTO {
    private String message;
    private UserCredential userCredential;

    public UserCredentialResponseDTO(String message) {
        this.message = message;
    }
}
