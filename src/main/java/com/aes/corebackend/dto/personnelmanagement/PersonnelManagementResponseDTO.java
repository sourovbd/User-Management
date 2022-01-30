package com.aes.corebackend.dto.personnelmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class PersonnelManagementResponseDTO {
    private String message;
    private boolean success;
    private Object data;
}
