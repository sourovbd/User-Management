package com.aes.corebackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    private String emailAddress;

    private String designation;

    private long employeeId;

    private String businessUnit;

    private String department;

    private String role;

}
