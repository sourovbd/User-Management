package com.aes.corebackend.dto;

import com.aes.corebackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class UserFinderResponseDTO {
    String message;
    User user;
}
