package com.aes.corebackend.dto;

import com.aes.corebackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsersFetchResponseDTO {
    String message;
    List<User> users;
}
