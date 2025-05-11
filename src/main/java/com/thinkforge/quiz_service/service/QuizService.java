package com.thinkforge.quiz_service.service;

import com.thinkforge.quiz_service.dto.CreateQuizRequestDTO;
import com.thinkforge.quiz_service.dto.QuizDTO;
import com.thinkforge.quiz_service.dto.UpdateQuizRequestDTO;
import com.thinkforge.quiz_service.entity.Quiz;
import com.thinkforge.quiz_service.entity.Teacher;
import com.thinkforge.quiz_service.repository.QuizRepository;
import com.thinkforge.quiz_service.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public UUID createQuiz(CreateQuizRequestDTO request) {

        UUID teacherId = request.getTeacherId();
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with teacher id: " + teacherId));

        Quiz quiz = new Quiz();
        quiz.setCreatedBy(teacher);
        quiz.setCreatedAt(Timestamp.from(Instant.now()));
        quiz.setUpdatedAt(Timestamp.from(Instant.now()));
        quiz.setSubject(request.getSubject());
        quiz.setTopic(request.getTopic());
        quiz.setGrade(request.getGrade());
        quiz.setDeadline(request.getDeadline());

        Quiz response = quizRepository.save(quiz);
        return response.getQuizId();
    }

    public QuizDTO getQuizByQuizId(UUID quizId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Cannot find Quiz with quiz id: " + quizId));

        return QuiztoQuizDTO(quiz);

    }

    public List<QuizDTO> getQuizByTeacherId(UUID teacherId) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with teacher id: " + teacherId));

        List<Quiz> quizList = quizRepository.findByCreatedBy(teacher);

        return quizList.stream()
                .map(QuizService::QuiztoQuizDTO)
                .toList();

    }

    public QuizDTO updateQuiz(UUID quizId, UpdateQuizRequestDTO request) {

        System.out.println(request.getGrade());

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Cannot find Quiz with quiz id: " + quizId));

        quiz.setUpdatedAt(Timestamp.from(Instant.now()));
        quiz.setGrade(request.getGrade());
        quiz.setSubject(request.getSubject());
        quiz.setTopic(request.getTopic());
        quiz.setDeadline(request.getDeadline());

        return QuiztoQuizDTO(quizRepository.save(quiz));

    }

    public void deleteQuiz(UUID quizId) {

        quizRepository.deleteById(quizId);

    }

    public static QuizDTO QuiztoQuizDTO(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
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
