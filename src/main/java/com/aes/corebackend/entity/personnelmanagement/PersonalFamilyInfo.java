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
public class PersonalFamilyInfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "maritalStatus")
    @Length(min = 0, max = 50, message = "Marital Status: Field must less than 50 characters")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Marital Status: Field cannot have numeric or special characters")
    private String maritalStatus;

    @Column(name = "fathersName")
    @Length(min = 0, max = 50, message = "Father's name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Father's name: Field cannot have numeric or special characters")
    private String fathersName;

    @Column(name = "mothersName")
    @Length(min = 0, max = 50, message = "Mother's Name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Mother's Name: Field cannot have numeric or special characters")
    private String mothersName;

    @Column(name = "spouseName")
    @Length(min = 0, max = 50, message = "Spouse's Name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Spouse's Name: Field cannot have numeric or special characters")
    private String spouseName;

    @OneToOne(cascade = CascadeType.ALL, optional = false) //@OneToOne defines a one-to-one relationship between 2 entities
    @JoinColumn(name = "user_id", referencedColumnName = "id") //@JoinColumn defines a foreign key column
    private User user;
}
