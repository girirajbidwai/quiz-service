package com.thinkforge.quiz_service.repository;

import com.thinkforge.quiz_service.entity.QuizStudentEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizStudentEvaluationRepository extends JpaRepository<QuizStudentEvaluation, UUID> {
}
