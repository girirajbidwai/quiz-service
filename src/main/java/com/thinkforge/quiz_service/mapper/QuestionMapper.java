package com.thinkforge.quiz_service.mapper;

import com.thinkforge.quiz_service.dto.GeneratedQuestionsDTO;
import com.thinkforge.quiz_service.entity.Question;
import com.thinkforge.quiz_service.entity.Quiz;

public class QuestionMapper {

    private QuestionMapper() {}

    public static Question fromDTO(GeneratedQuestionsDTO dto, Quiz quiz) {
        Question q = new Question();
        q.setQuiz(quiz);
        q.setQuestionText(dto.getQuestionText());
        q.setMarks(dto.getMarks());
        q.setNegativeMarks(dto.getNegativeMarks());
        q.setOptionA(dto.getOptionA());
        q.setOptionB(dto.getOptionB());
        q.setOptionC(dto.getOptionC());
        q.setOptionD(dto.getOptionD());
        q.setCorrectOption(dto.getCorrectOption());
        q.setHint(dto.getHint());
        return q;
    }
}
