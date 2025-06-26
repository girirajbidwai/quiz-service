package com.thinkforge.quiz_service.repository;

import com.thinkforge.quiz_service.entity.Question;
import com.thinkforge.quiz_service.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByQuiz(Quiz quiz);
}
