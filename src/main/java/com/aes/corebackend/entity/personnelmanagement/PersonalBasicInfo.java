package com.aes.corebackend.entity.personnelmanagement;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.enumeration.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PersonalBasicInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName")
    @Length(min = 0, max = 50, message = "First name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "First name field cannot have numeric or special characters")
    private String firstName;

    @Column(name = "lastName")
    @Length(min = 0, max = 50, message = "Last name field can be at max 50 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Last name field cannot have numeric or special characters")
    private String lastName;

    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL, optional = false) //@OneToOne defines a one-to-one relationship between 2 entities
    @JoinColumn(name = "user_id", referencedColumnName = "id") //@JoinColumn defines a foreign key column
    private User user;
}
