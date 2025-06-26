package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizQuestionResponseDTO {

    private Integer numOfQuestions;
    private List<QuestionDTO> questions;

}
