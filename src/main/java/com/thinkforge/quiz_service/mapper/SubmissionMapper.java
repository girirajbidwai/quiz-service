package com.thinkforge.quiz_service.mapper;

import com.thinkforge.quiz_service.dto.QuizSubmissionAnalysisDTO;
import com.thinkforge.quiz_service.entity.QuizSubmission;

public class SubmissionMapper {

    private SubmissionMapper() {}

    public static QuizSubmissionAnalysisDTO toAnalysisDTO(QuizSubmission submission) {
        QuizSubmissionAnalysisDTO dto = new QuizSubmissionAnalysisDTO();
        dto.setStudentId(submission.getStudent().getStudentId());
        dto.setSubmissionTime(submission.getSubmittedAt());
        dto.setObtainedScore(submission.getScore());
        dto.setMaxScore(submission.getMaxScore());
        return dto;
    }
}
