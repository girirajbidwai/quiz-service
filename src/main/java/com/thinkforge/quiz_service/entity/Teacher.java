package com.thinkforge.quiz_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue
    private UUID teacherId;

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

    @NotBlank
    private String secondarySchool;

    @DecimalMin("0.00")
    private BigDecimal secondaryMarks;

    @NotBlank
    private String higherSecondarySchool;

    @DecimalMin("0.00")
    private BigDecimal higherSecondaryMarks;

    @NotBlank
    private String ugDegree;

    @NotBlank
    private String ugCollege;

    @Min(1900)
    private int ugYear;

    @DecimalMin("0.00")
    private BigDecimal ugMarks;

    private String pgDegree;
    private String pgCollege;
    private Integer pgYear;
    private BigDecimal pgMarks;
}
