package com.thinkforge.quiz_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private UUID questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonBackReference
    private Quiz quiz;

    @NotBlank
    @Column(name = "question_text", columnDefinition = "TEXT", nullable = false)
    private String questionText;

    @Min(0)
    @Column(name = "marks", nullable = false)
    private int marks;

    @Min(0)
    @Column(name = "negative_marks", nullable = false)
    private int negativeMarks;

    @NotBlank
    @Column(name = "option_a", nullable = false)
    private String optionA;

    @NotBlank
    @Column(name = "option_b", nullable = false)
    private String optionB;

    @NotBlank
    @Column(name = "option_c", nullable = false)
    private String optionC;

    @NotBlank
    @Column(name = "option_d", nullable = false)
    private String optionD;

    @Pattern(regexp = "[A-D]", message = "Correct option must be A, B, C, or D")
    @Column(name = "correct_option", nullable = false, length = 1)
    private String correctOption;

    @Column(name = "hint", columnDefinition = "TEXT")
    private String hint;
}
