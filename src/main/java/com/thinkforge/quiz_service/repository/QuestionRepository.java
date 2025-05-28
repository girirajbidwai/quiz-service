package com.thinkforge.quiz_service.repository;

import com.thinkforge.quiz_service.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    Question findByEvaluationId(UUID evaluationId);
}
