package com.aes.corebackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PersonalFamilyInfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "materialStatus")
    private String materialStatus;

    @Column(name = "fathersName")
    private String fathersName;

    @Column(name = "mothersName")
    private String mothersName;

    @Column(name = "spouseName")
    private String spouseName;
}
