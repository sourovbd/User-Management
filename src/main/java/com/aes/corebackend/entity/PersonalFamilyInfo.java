package com.aes.corebackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PersonalFamilyInfo {
    @Column(name = "materialStatus")
    private String materialStatus;

    @Column(name = "fathersName")
    private String fathersName;

    @Column(name = "mothersName")
    private String mothersName;

    @Column(name = "spouseName")
    private String spouseName;
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
