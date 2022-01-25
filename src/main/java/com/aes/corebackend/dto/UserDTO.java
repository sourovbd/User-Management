package com.aes.corebackend.dto;

import com.aes.corebackend.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private long id;

    private String emailAddress;

    private String designation;

    private long employeeId;

    private String businessUnit;

    private String department;

    public User dtoToUser(UserDTO userDTO){
        User user = new User();
        user.setEmployeeId(userDTO.getEmployeeId());
        user.setEmailAddress(userDTO.getEmailAddress());
        user.setBusinessUnit(userDTO.getBusinessUnit());
        user.setDepartment(userDTO.getDepartment());
        user.setDesignation(userDTO.getDesignation());
        return user;
    }
}
