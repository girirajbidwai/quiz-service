package com.thinkforge.quiz_service.controller;

import com.thinkforge.quiz_service.dto.*;
import com.thinkforge.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/quiz/")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/create")
    public ResponseEntity<UUID> createQuiz(@RequestBody CreateQuizRequestDTO request) {

        UUID quizId = quizService.createQuiz(request);
        return ResponseEntity.ok(quizId);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<GetQuestionsDTO>> generateQuiz(@RequestBody CreateQuizRequestDTO request) {

        List<GetQuestionsDTO> quizId = quizService.generateQuiz(request);
        return ResponseEntity.ok(quizId);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDTO> getQuizByQuizId(@PathVariable UUID quizId) {

        QuizDTO quiz = quizService.getQuizByQuizId(quizId);
        return ResponseEntity.ok(quiz);

    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<QuizDTO>> getQuizByTeacherId(@PathVariable UUID teacherId) {

        List<QuizDTO> quizList = quizService.getQuizByTeacherId(teacherId);
        return ResponseEntity.ok(quizList);

    }

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable UUID quizId, @RequestBody UpdateQuizRequestDTO request) {

        QuizDTO quiz = quizService.updateQuiz(quizId, request);
        return ResponseEntity.ok(quiz);

    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable UUID quizId) {

        quizService.deleteQuiz(quizId);
        return ResponseEntity.ok("Quiz deleted successfully!!");

    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<?> submitQuiz(@PathVariable UUID quizId, @RequestBody QuizSubmissionRequest request) {

        quizService.submitQuiz(quizId, request);
        return ResponseEntity.ok("Quiz submitted successfully!!");

    }

    @GetMapping("/quiz-analysis/{quizId}")
    public ResponseEntity<?> getQuizAnalysisByQuiz(@PathVariable UUID quizId) {

        QuizAnalysisByQuizIdResponseDTO response = quizService.getQuizAnalysisByQuiz(quizId);
        return ResponseEntity.ok(response);
    }

}
