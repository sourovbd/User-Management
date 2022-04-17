package com.sv.corebackend.dto.usermanagement;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ForgotPasswordDTO {

    @Pattern(regexp = "[a-zA-Z0-9.]*[@][a-zA-Z]+\\.(com|net|org)", message = "email id is invalid")
    private String emailAddress;
}
