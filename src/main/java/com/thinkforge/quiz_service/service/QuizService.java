package com.thinkforge.quiz_service.service;

import com.thinkforge.quiz_service.dto.*;
import com.thinkforge.quiz_service.entity.*;
import com.thinkforge.quiz_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizStudentEvaluationRepository quizStudentEvaluationRepository;

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;

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

    public void submitQuiz(UUID quizId, QuizSubmissionRequest request) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Cannot find Quiz with quiz id: " + quizId));

        UUID studentId = request.getStudentId();

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Cannot find student with student id: " +  studentId));

        int maxScore = 0, score = 0;

        for(AnswerDTO answer: request.getAnswers()) {

            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Cannot find Question with question id: " + answer.getQuestionId()));

            maxScore++;
            if(Objects.equals(answer.getSelectedOption(), question.getCorrectOption())) score++;

            QuizStudentEvaluation evaluation = new QuizStudentEvaluation();
            evaluation.setStudentId(studentId);
            evaluation.setQuiz(quiz);
            evaluation.setQuestion(question);
            evaluation.setSelectedOption(answer.getSelectedOption());
            evaluation.setSubmittedAt(Timestamp.from(Instant.now()));

            quizStudentEvaluationRepository.save(evaluation);

        }

        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setQuiz(quiz);
        quizSubmission.setStudent(student);
        quizSubmission.setScore(score);
        quizSubmission.setMaxScore(maxScore);
        quizSubmission.setSubmittedAt(Timestamp.from(Instant.now()));

        quizSubmissionRepository.save(quizSubmission);

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
