package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionDTO {

    private UUID questionId;

    private UUID quizId;

    private String questionText;

    private int marks;

    private int negativeMarks;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String correctOption;

    private String hint;
}
