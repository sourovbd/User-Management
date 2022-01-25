package com.aes.corebackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PersonalAttributes {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "religion")
    private String religion;

    @Column(name = "bloodGroup")
    private String bloodGroup;

    @Column(name = "birthPlace")
    private String birthPlace;

    @Column(name = "nationality")
    private String nationality;
}
