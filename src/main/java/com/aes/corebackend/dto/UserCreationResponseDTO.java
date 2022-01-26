package com.aes.corebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserCreationResponseDTO {
    private String message;
    private boolean success;

}
