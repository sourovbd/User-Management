package com.aes.corebackend.entity.personnelmanagement;

import com.aes.corebackend.entity.usermanagement.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PersonalEducationInfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "degreeName")
    @Length(min = 0, max = 50, message = "Degree Name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Degree Name: Field cannot have numeric or special characters")
    private String degreeName;

    @Column(name = "institutionName")
    @Length(min = 0, max = 50, message = "Institution Name: Field must less than 50 characters")
    @Pattern(regexp = "^([a-zA-Z]+\\s)*[a-zA-Z]+$", message = "Institution Name: Field cannot have numeric or special characters")
    private String institutionName;

    @Column(name = "gpaScale")
    @Max(value = 5, message = "Please convert GPA Scale to Maximum 5")
    private float gpaScale;

    @Column(name = "cgpa")
    @Max(value = 5, message = "Please convert CGPA Scale to Maximum 5")
    private float cgpa;

    @Column(name = "passingYear")
    @Pattern(regexp = "[0-9]{4}", message = "Year format incorrect")
    private String passingYear;

    @ManyToOne
    private User user;
}
