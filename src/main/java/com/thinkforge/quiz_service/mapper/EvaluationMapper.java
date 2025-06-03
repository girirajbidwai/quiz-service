package com.thinkforge.quiz_service.mapper;

import com.thinkforge.quiz_service.dto.StudentQuestionAnalysisDTO;
import com.thinkforge.quiz_service.entity.QuizStudentEvaluation;

public class EvaluationMapper {

    private EvaluationMapper() {}

    public static StudentQuestionAnalysisDTO toQuestionAnalysisDTO(QuizStudentEvaluation eval) {
        StudentQuestionAnalysisDTO dto = new StudentQuestionAnalysisDTO();
        dto.setQuestionId(eval.getQuestion().getQuestionId());
        dto.setOptionA(eval.getQuestion().getOptionA());
        dto.setOptionB(eval.getQuestion().getOptionB());
        dto.setOptionC(eval.getQuestion().getOptionC());
        dto.setOptionD(eval.getQuestion().getOptionD());
        dto.setSelectedOption(eval.getSelectedOption());
        dto.setCorrectOption(eval.getQuestion().getCorrectOption());
        return dto;
    }
}
