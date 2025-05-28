package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuizAnalysisByQuizIdResponseDTO {
    private UUID quizId;
    List<QuizAnalysisByQuizIdDTO> studentData;
}
