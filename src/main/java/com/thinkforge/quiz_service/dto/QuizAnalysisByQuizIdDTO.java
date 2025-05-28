package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuizAnalysisByQuizIdDTO {
    private UUID studentId;
    private List<QuizAnalysisByQuizIdQuestionDTO> questionData;
    private Integer obtainedScore;
    private Integer maxScore;
}
