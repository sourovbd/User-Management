package com.aes.corebackend.entity.personnelmanagement;


import com.aes.corebackend.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class PersonalJobExperience {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employer_name")
    private String employerName;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;
    @Column(name = "designation")
    private String designation;
    @Column(name = "responsibilities")
    private String responsibilities;

    @ManyToOne
    private User user;
}
