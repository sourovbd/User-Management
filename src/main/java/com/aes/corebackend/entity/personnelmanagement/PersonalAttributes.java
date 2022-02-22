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
public class PersonalAttributes {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "religion")
    @Length(min = 3, max = 20, message = "Religion Field must be within 3 to 20 characters")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Religion Field cannot have numeric or special characters")
    private String religion;

    @Column(name = "bloodGroup")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Blood Group: Invalid")
    private String bloodGroup;

    @Column(name = "birthPlace")
    @Length(min = 0, max = 255, message = "Birth place: Character Length exceeds of 255")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Birth place: Field cannot have numeric or special characters")
    private String birthPlace;

    @Column(name = "nationality")
    @Length(min = 0, max = 30, message = "Nationality: Character Length exceeds of 30")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Nationality: Field cannot have numeric or special characters")
    private String nationality;

    @OneToOne(cascade = CascadeType.ALL, optional = false) //@OneToOne defines a one-to-one relationship between 2 entities
    @JoinColumn(name = "user_id", referencedColumnName = "id") //@JoinColumn defines a foreign key column
    private User user;
}
