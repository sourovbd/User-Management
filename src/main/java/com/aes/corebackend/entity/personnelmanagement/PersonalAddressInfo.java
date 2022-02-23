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
public class PersonalAddressInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "presentAddress")
    @Length(min = 0, max = 255, message = "Present address field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Present address field cannot have numeric or special characters")
    private String presentAddress;

    @Column(name = "permanentAddress")
    @Length(min = 0, max = 255, message = "Permanent address field can be at max 255 characters long")
    @Pattern(regexp = "^[a-zA-z]+$", message = "Permanent address field cannot have numeric or special characters")
    private String permanentAddress;

    @OneToOne(cascade = CascadeType.ALL, optional = false) //@OneToOne defines a one-to-one relationship between 2 entities
    @JoinColumn(name = "user_id", referencedColumnName = "id") //@JoinColumn defines a foreign key column
    private User user;
}
