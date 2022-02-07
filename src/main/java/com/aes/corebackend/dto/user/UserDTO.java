package com.aes.corebackend.dto.user;

import com.aes.corebackend.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private String emailAddress;
    private String designation;
    private String employeeId;
    private String businessUnit;
    private String department;

    public User getUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmailAddress(userDTO.getEmailAddress());
        user.setDepartment(userDTO.getDepartment());
        user.setDesignation(userDTO.getDesignation());
        user.setEmployeeId(userDTO.getEmployeeId());
        user.setBusinessUnit(userDTO.getBusinessUnit());
        return user;
    }
}
