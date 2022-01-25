package com.aes.corebackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PersonalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "religion")
    private String religion;

    @Column(name = "bloodGroup")
    private String bloodGroup;

    @Column(name = "birthPlace")
    private String birthPlace;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "nationalID")
    private String nationalID;

    @Column(name = "eTin")
    private String eTin;

    @Column(name = "materialStatus")
    private String materialStatus;

    @Column(name = "fathersName")
    private String fathersName;

    @Column(name = "mothersName")
    private String mothersName;

    @Column(name = "spouseName")
    private String spouseName;

    @Column(name = "presentAddress")
    private String presentAddress;

    @Column(name = "permanentAddress")
    private String permanentAddress;
}
