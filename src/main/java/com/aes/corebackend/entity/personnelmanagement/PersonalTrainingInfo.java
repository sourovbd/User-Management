package com.aes.corebackend.entity.personnelmanagement;


import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;

//Database definitions are written in the Entity
@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PersonalTrainingInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "program_name")
    @Length(min = 0, max = 50, message = "Program name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Program name field cannot have special characters")
    private String programName;

    @Column(name = "institute_name")
    @Length(min = 0, max = 50, message = "Training institute name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Training institute name field cannot have special characters")
    private String trainingInstitute;

    @Column(name = "description")
    @Length(min = 0, max = 255, message = "Description field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Description field cannot have special characters")
    private String description;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = Constants.BD_DATE_FORMAT)
    @Past(message = "The start date must be in the past")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = Constants.BD_DATE_FORMAT)
    @Past(message = "The end date must be in the past")
    private LocalDate endDate;

    @ManyToOne
    private User user;
}
