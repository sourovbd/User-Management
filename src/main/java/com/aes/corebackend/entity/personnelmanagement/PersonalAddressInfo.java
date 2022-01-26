package com.aes.corebackend.entity.personnelmanagement;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PersonalAddressInfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "presentAddress")
    private String presentAddress;

    @Column(name = "permanentAddress")
    private String permanentAddress;
}
