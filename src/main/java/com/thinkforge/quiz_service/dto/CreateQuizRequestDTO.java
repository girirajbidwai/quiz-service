package com.thinkforge.quiz_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class CreateQuizRequestDTO {

    @NotNull(message = "Teacher ID is required")
    private UUID teacherId;

    @NotNull(message = "Grade is required")
    @Min(value = 1, message = "Grade must be at least 1")
    private Integer grade;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotNull(message = "Number of questions is required")
    @Min(value = 1, message = "There must be at least 1 question")
    private Integer numOfQuestions;

    @Future(message = "Deadline must be in the future")
    private Timestamp deadline;
}
