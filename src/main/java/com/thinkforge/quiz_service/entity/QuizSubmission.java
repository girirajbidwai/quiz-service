package com.thinkforge.quiz_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "quiz_submission", uniqueConstraints = @UniqueConstraint(columnNames = {"quiz_id", "student_id"}))
public class QuizSubmission {

    @Id
    @GeneratedValue
    private UUID submissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonBackReference
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @Min(0)
    private int score;

    @Min(0)
    private int maxScore;

    @Column(nullable = false)
    private Timestamp submittedAt;
}
