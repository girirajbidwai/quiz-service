package com.thinkforge.quiz_service.repository;

import com.thinkforge.quiz_service.entity.Quiz;
import com.thinkforge.quiz_service.entity.QuizSubmission;
import com.thinkforge.quiz_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, UUID> {
    QuizSubmission findByStudentAndQuiz(Student student, Quiz quiz);

    List<QuizSubmission> findAllByQuiz(Quiz quiz);
}
