package com.thinkforge.quiz_service.mapper;

import com.thinkforge.quiz_service.dto.QuizMetadataDTO;
import com.thinkforge.quiz_service.entity.Quiz;

public class QuizMapper {

    private QuizMapper() {}

    public static QuizMetadataDTO toMetadataDTO(Quiz quiz) {
        QuizMetadataDTO dto = new QuizMetadataDTO();
        dto.setQuizId(quiz.getQuizId());
        dto.setCreatedBy(quiz.getCreatedBy().getTeacherId());
        dto.setCreatedAt(quiz.getCreatedAt());
        dto.setUpdatedAt(quiz.getUpdatedAt());
        dto.setSubject(quiz.getSubject());
        dto.setTopic(quiz.getTopic());
        dto.setGrade(quiz.getGrade());
        dto.setDeadline(quiz.getDeadline());
        return dto;
    }
}
