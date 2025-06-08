package com.thinkforge.quiz_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateQuizRequestDTO {

    @Min(value = 1, message = "Grade must be at least 1")
    private Integer grade;

    @Size(min = 2, max = 100, message = "Subject must be between 2 and 100 characters")
    private String subject;

    @Size(min = 2, max = 100, message = "Topic must be between 2 and 100 characters")
    private String topic;

    private Timestamp deadline;
}
