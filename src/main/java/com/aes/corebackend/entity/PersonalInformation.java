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


    private PersonalBasicInfo personalBasicInfo;
    private PersonalAttributes personalAttributes;
    private PersonalIdenitficationInfo personalIdenitficationInfo;
    private PersonalFamilyInfo personalFamilyInfo;
    private PersonalAddressInfo personalAddressInfo;
}
