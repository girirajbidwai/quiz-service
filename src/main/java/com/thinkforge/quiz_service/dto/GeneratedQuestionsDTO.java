package com.thinkforge.quiz_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GeneratedQuestionsDTO {

    @NotBlank
    private String questionText;

    @Min(0)
    private int marks;

    @Min(0)
    private int negativeMarks;

    @NotBlank
    private String optionA;

    @NotBlank
    private String optionB;

    @NotBlank
    private String optionC;

    @NotBlank
    private String optionD;

    @Pattern(regexp = "[A-D]", message = "Correct option must be A, B, C, or D")
    private String correctOption;

    private String hint;

}
