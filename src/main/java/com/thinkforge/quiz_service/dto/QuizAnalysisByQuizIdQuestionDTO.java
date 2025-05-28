package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QuizAnalysisByQuizIdQuestionDTO {
    private UUID questionId;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String selectedOption;
    private String correctOption;
}
