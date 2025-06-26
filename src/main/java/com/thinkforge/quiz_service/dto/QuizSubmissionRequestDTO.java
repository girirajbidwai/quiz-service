package com.thinkforge.quiz_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuizSubmissionRequestDTO {

    @NotNull(message = "Student ID must not be null")
    private UUID studentId;

    @NotEmpty(message = "Answers list must not be empty")
    @Valid
    private List<AnswerDTO> answers;
}
