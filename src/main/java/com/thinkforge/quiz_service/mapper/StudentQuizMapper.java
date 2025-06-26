package com.thinkforge.quiz_service.mapper;

import com.thinkforge.quiz_service.dto.StudentQuizAnalysisDTO;
import com.thinkforge.quiz_service.entity.QuizStudentEvaluation;
import com.thinkforge.quiz_service.entity.QuizSubmission;

import java.util.List;
import java.util.UUID;

public class StudentQuizMapper {

    private StudentQuizMapper() {}

    public static StudentQuizAnalysisDTO toAnalysisDTO(UUID studentId, QuizSubmission submission, List<QuizStudentEvaluation> evaluations) {
        StudentQuizAnalysisDTO dto = new StudentQuizAnalysisDTO();
        dto.setStudentId(studentId);
        dto.setObtainedScore(submission.getScore());
        dto.setMaxScore(submission.getMaxScore());
        dto.setQuestionData(evaluations.stream().map(EvaluationMapper::toQuestionAnalysisDTO).toList());
        return dto;
    }
}
