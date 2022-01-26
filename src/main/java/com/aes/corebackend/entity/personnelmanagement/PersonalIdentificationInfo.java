package com.aes.corebackend.entity.personnelmanagement;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PersonalIdentificationInfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nationalID")
    private String nationalID;

    @Column(name = "eTin")
    private String eTin;
}
