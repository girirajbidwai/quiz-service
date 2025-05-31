package com.thinkforge.quiz_service.repository;

import com.thinkforge.quiz_service.entity.Quiz;
import com.thinkforge.quiz_service.entity.QuizStudentEvaluation;
import com.thinkforge.quiz_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizStudentEvaluationRepository extends JpaRepository<QuizStudentEvaluation, UUID> {
    List<QuizStudentEvaluation> findAllByStudentIdAndQuiz(UUID studentId, Quiz quiz);

}
