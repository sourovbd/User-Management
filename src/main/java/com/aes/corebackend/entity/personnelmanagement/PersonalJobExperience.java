package com.aes.corebackend.entity.personnelmanagement;


import com.aes.corebackend.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class PersonalJobExperience {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employer_name")
    @Length(min = 0, max = 50, message = "Employer name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Employer name field cannot have special characters")
    private String employerName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "designation")
    @Length(min = 0, max = 50, message = "Designation field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Designation field cannot have special characters")
    private String designation;

    @Column(name = "responsibilities")
    @Length(min = 0, max = 255, message = "Responsibilities field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Responsibilities field cannot have numeric or special characters")
    private String responsibilities;

    @ManyToOne
    private User user;
}
