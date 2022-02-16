package com.aes.corebackend.entity.personnelmanagement;

import com.aes.corebackend.entity.usermanagement.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
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

    @OneToOne(cascade = CascadeType.ALL, optional = false) //@OneToOne defines a one-to-one relationship between 2 entities
    @JoinColumn(name = "user_id", referencedColumnName = "id") //@JoinColumn defines a foreign key column
    private User user;
}
