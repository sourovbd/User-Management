package com.aes.corebackend.entity.personnelmanagement;

import com.aes.corebackend.entity.usermanagement.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PersonalIdentificationInfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nationalID")
    @Length(min = 10, max = 13, message = "NationalID: Length should be minimum 10, maximum 13 characters")
    @Pattern(regexp = "^[0-9]+$", message = "NationalID: Field cannot have alphabetic or special characters")
    private String nationalID;

    @Column(name = "eTin")
    @Length(min = 12, max = 12, message = "E-Tin Number: Length should be 12 characters")
    @Pattern(regexp = "^[0-9]+$", message = "E-Tin: Field cannot have alphabetic or special characters")
    private String etin;

    @OneToOne(cascade = CascadeType.ALL, optional = false) //@OneToOne defines a one-to-one relationship between 2 entities
    @JoinColumn(name = "user_id", referencedColumnName = "id") //@JoinColumn defines a foreign key column
    private User user;
}
