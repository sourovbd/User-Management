package com.aes.corebackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PersonalBasicInfo {
    @Id
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "gender")
    private String gender;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
