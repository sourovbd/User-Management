package com.aes.corebackend.entity.personnelmanagement;

import com.aes.corebackend.entity.User;
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

    @Column(name = "maritalStatus")
    private String maritalStatus;

    @Column(name = "fathersName")
    private String fathersName;

    @Column(name = "mothersName")
    private String mothersName;

    @Column(name = "spouseName")
    private String spouseName;

    @OneToOne(cascade = CascadeType.ALL, optional = false) //@OneToOne defines a one-to-one relationship between 2 entities
    @JoinColumn(name = "user_id", referencedColumnName = "id") //@JoinColumn defines a foreign key column
    private User user;
}
