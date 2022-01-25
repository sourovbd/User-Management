package com.aes.corebackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PersonalIdenitficationInfo {
    @Column(name = "nationalID")
    private String nationalID;

    @Column(name = "eTin")
    private String eTin;
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
