package com.aes.corebackend.dto;

import com.aes.corebackend.entity.UserCredential;
import lombok.Data;

@Data
public class UserCredentialDTO {

    private static final long serialVersionUID = 1L;

    private long id;

    private String employeeId;

    private String password;

    private String role;

    public UserCredentialDTO from(UserCredential userCredential) {
        UserCredentialDTO dto = new UserCredentialDTO();
        dto.setId(userCredential.getId());
        dto.setEmployeeId(userCredential.getEmployeeId());
        dto.setPassword(userCredential.getPassword());
        dto.setRole(userCredential.getRoles());
        return dto;
    }

    public UserCredential to(UserCredentialDTO dto) {
        UserCredential userCredential = new UserCredential();
        userCredential.setId(dto.getId());
        userCredential.setEmployeeId(dto.getEmployeeId());
        userCredential.setPassword(dto.getPassword());
        userCredential.setRoles(dto.getRole());
        return userCredential;
    }
}
