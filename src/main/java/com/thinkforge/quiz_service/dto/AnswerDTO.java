package com.thinkforge.quiz_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class AnswerDTO {

    @NotNull(message = "Question ID must not be null")
    private UUID questionId;

    @NotNull(message = "Selected option must not be null")
    @Pattern(regexp = "[ABCD]", message = "Selected option must be one of: A, B, C, D")
    private String selectedOption;
}
