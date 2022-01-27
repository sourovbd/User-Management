package com.aes.corebackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;


@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "emailAddress", unique = true)
    private String emailAddress;
    @Column(name = "designation")
    private String designation;
    @Column(name = "employeeId", unique = true)
    private long employeeId;
    @Column(name = "businessUnit")
    private String businessUnit;
    @Column(name = "department")
    private String department;

}
