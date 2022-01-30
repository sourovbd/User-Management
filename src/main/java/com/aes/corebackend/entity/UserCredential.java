package com.aes.corebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "employeeId", nullable = false, unique = true)
    private String employeeId;

    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

}
