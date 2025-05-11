package com.thinkforge.quiz_service.repository;

import com.thinkforge.quiz_service.entity.Quiz;
import com.thinkforge.quiz_service.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    List<Quiz> findByCreatedBy(Teacher teacher);

}
