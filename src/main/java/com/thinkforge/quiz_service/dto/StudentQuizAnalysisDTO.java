package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class StudentQuizAnalysisDTO {
    private UUID studentId;
    private List<StudentQuestionAnalysisDTO> questionData;
    private Integer obtainedScore;
    private Integer maxScore;
}
