package com.aes.corebackend.entity.personnelmanagement;

import com.aes.corebackend.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PersonalAddressInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "presentAddress")
    private String presentAddress;

    @Column(name = "permanentAddress")
    private String permanentAddress;

    @OneToOne(cascade = CascadeType.ALL, optional = false) //@OneToOne defines a one-to-one relationship between 2 entities
    @JoinColumn(name = "user_id", referencedColumnName = "id") //@JoinColumn defines a foreign key column
    private User user;
}
