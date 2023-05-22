package com.moviecollection.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[{\\p{L}}{0-9}]{2,100}$", message = "Name is not valid. No special characters should be used and size should be between 2 and 100.")
    private String name;
    
    private String role = "user";

    @NotBlank(message = "Email is mandatory")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,32}$", message = "Minimum 8 characters, use at least one letter and one number.")
    private String password;

    @NotBlank(message = "Date of birth is mandatory")
    private Date dateOfBirth;


    private String createdAt;
    private String updatedAt;

}
