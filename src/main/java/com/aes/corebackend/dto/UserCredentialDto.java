package com.aes.corebackend.dto;

import com.aes.corebackend.entity.UserCredential;
import lombok.Data;

@Data
public class UserCredentialDto {

    private static final long serialVersionUID = 1L;

    private long id;

    private long employeeId;

    private String password;

    private String role;

    public UserCredentialDto from(UserCredential userCredential) {
        UserCredentialDto dto = new UserCredentialDto();
        dto.setId(userCredential.getId());
        dto.setEmployeeId(userCredential.getEmployeeId());
        dto.setPassword(userCredential.getPassword());
        dto.setRole(userCredential.getRole());
        return dto;
    }

    public UserCredential to(UserCredentialDto dto) {
        UserCredential userCredential = new UserCredential();
        userCredential.setId(dto.getId());
        userCredential.setEmployeeId(dto.getEmployeeId());
        userCredential.setPassword(dto.getPassword());
        userCredential.setRole(dto.getRole());
        return userCredential;
    }
}
