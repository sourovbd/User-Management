package com.aes.corebackend.entity.personnelmanagement;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//Database definitions are written in the Entity
@Data
@Entity
@NoArgsConstructor
public class PersonalTrainingInfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "program_name")
    private String programName;
    @Column(name = "institute_name")
    private String trainingInstitute;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;
}
