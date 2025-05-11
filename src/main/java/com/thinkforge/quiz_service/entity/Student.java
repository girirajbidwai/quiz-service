package com.thinkforge.quiz_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue
    private UUID studentId;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    private String firstname;

    private String middlename;

    @NotBlank
    private String lastname;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    private String gender;

    @NotNull
    private LocalDate dateOfBirth;

    @Min(1)
    private int grade;

    @NotBlank
    private String schoolName;
}
