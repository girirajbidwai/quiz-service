package com.thinkforge.quiz_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "quiz_student_evaluation", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "quiz_id", "question_id"})
})
public class QuizStudentEvaluation {

    @Id
    @GeneratedValue
    @Column(name = "evaluation_id", nullable = false, updatable = false)
    private UUID evaluationId;

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Pattern(regexp = "[A-D]", message = "Selected option must be A, B, C, or D")
    @Column(name = "selected_option", nullable = false, length = 1)
    private String selectedOption;

    @Column(name = "submitted_at", nullable = false)
    private Timestamp submittedAt = Timestamp.from(Instant.now());
}
