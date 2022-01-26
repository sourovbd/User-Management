package com.aes.corebackend.entity;

import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "emailAddress")
    private String emailAddress;
    @Column(name = "designation")
    private String designation;
    @Column(name = "employeeId")
    private String employeeId;
    @Column(name = "businessUnit")
    private String businessUnit;
    @Column(name = "department")
    private String department;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user") //mappedBy value points to the relationship owner
    private PersonalBasicInfo basicInfo;
}