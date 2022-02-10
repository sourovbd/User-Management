package com.aes.corebackend.entity.personnelmanagement;

import com.aes.corebackend.entity.usermanagement.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne
    private User user;
}
