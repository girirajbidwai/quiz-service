package com.thinkforge.quiz_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue
    private UUID quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Teacher createdBy;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

    @NotBlank
    private String subject;

    @NotBlank
    private String topic;

    @Min(1)
    private int grade;

    @Column(nullable = false)
    private Timestamp deadline;
}
