package com.aes.corebackend.entity.personnelmanagement;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class PersonalEducationInfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "degreeName")
    private String degreeName;
    @Column(name = "institutionName")
    private String institutionName;
    @Column(name = "gpaScale")
    private float gpaScale;
    @Column(name = "cgpa")
    private float cgpa;
    @Column(name = "passingYear")
    private String passingYear;

}
