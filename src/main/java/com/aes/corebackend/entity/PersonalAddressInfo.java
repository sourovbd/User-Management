package com.aes.corebackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PersonalAddressInfo {
    @Column(name = "presentAddress")
    private String presentAddress;

    @Column(name = "permanentAddress")
    private String permanentAddress;
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
