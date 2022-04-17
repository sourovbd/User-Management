package com.sv.corebackend.dto.usermanagement;

import com.sv.corebackend.annotation.ValidPassword;
import com.sv.corebackend.entity.UserCredential;
import lombok.Data;

@Data
public class UserCredentialDTO {

    private static final long serialVersionUID = 1L;

    private long id;

    private String employeeId;

    @ValidPassword
    private String password;

    private boolean active;

    private String roles;

    public UserCredentialDTO from(UserCredential userCredential) {
        UserCredentialDTO dto = new UserCredentialDTO();
        dto.setId(userCredential.getId());
        dto.setEmployeeId(userCredential.getEmployeeId());
        dto.setPassword(userCredential.getPassword());
        dto.setActive(userCredential.isActive());
        dto.setRoles(userCredential.getRoles());
        return dto;
    }

    public UserCredential to(UserCredentialDTO dto) {
        UserCredential userCredential = new UserCredential();
        userCredential.setId(dto.getId());
        userCredential.setEmployeeId(dto.getEmployeeId());
        userCredential.setPassword(dto.getPassword());
        userCredential.setActive(dto.isActive());
        userCredential.setRoles(dto.getRoles());
        return userCredential;
    }
}
