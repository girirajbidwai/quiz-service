package com.thinkforge.quiz_service.controller;

import com.thinkforge.quiz_service.dto.CreateQuizRequestDTO;
import com.thinkforge.quiz_service.dto.QuizDTO;
import com.thinkforge.quiz_service.dto.UpdateQuizRequestDTO;
import com.thinkforge.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

}
