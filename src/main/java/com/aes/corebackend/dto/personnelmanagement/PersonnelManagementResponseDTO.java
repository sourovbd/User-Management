package com.aes.corebackend.dto.personnelmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonnelManagementResponseDTO {
    private String message;
    private boolean success;
}
