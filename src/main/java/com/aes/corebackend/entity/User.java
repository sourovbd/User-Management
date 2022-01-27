package com.aes.corebackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "emailAddress", unique = true)
    @Pattern(regexp = "[a-zA-Z0-9.]*[@][a-zA-Z]+\\.(com|net|org)")
    @NotBlank(message = "email is mandatory")
    private String emailAddress;
    @Column(name = "designation")
    private String designation;
    @NotBlank(message = "employee id is mandatory")
    @Column(name = "employeeId", unique = true)
    private String employeeId;
    @Column(name = "businessUnit")
    private String businessUnit;
    @Column(name = "department")
    private String department;

}
