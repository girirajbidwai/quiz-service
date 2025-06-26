package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class QuizSubmissionAnalysisDTO {
    private UUID studentId;
    private Timestamp submissionTime;
    private Integer obtainedScore;
    private Integer maxScore;
}
