package com.thinkforge.quiz_service.controller;

import com.thinkforge.quiz_service.dto.*;
import com.thinkforge.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    // ---------- Quiz Metadata ----------

    @GetMapping
    public ResponseEntity<List<QuizMetadataDTO>> getAllQuiz() {
        return ResponseEntity.ok(quizService.getAllQuiz());
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizMetadataDTO> getQuizById(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizByQuizId(quizId));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<QuizMetadataDTO>> getQuizByTeacherId(@PathVariable UUID teacherId) {
        return ResponseEntity.ok(quizService.getQuizByTeacherId(teacherId));
    }

    @PostMapping("/generate")
    public ResponseEntity<List<GeneratedQuestionsDTO>> generateQuiz(@RequestBody CreateQuizRequestDTO request) {
        return ResponseEntity.ok(quizService.generateQuiz(request));
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizMetadataDTO> updateQuiz(
            @PathVariable UUID quizId,
            @RequestBody UpdateQuizRequestDTO request) {
        return ResponseEntity.ok(quizService.updateQuiz(quizId, request));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable UUID quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.ok("Quiz deleted successfully");
    }

    // ---------- Quiz Submission ----------

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<String> submitQuiz(
            @PathVariable UUID quizId,
            @RequestBody QuizSubmissionRequestDTO request) {
        quizService.submitQuiz(quizId, request);
        return ResponseEntity.ok("Quiz submitted successfully");
    }

    @GetMapping("/{quizId}/submissions")
    public ResponseEntity<List<QuizSubmissionAnalysisDTO>> getAllSubmissions(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getAllSubmissionsForQuiz(quizId));
    }

    @GetMapping("/{quizId}/submission/{studentId}")
    public ResponseEntity<StudentQuizAnalysisDTO> getStudentSubmission(
            @PathVariable UUID quizId,
            @PathVariable UUID studentId) {
        return ResponseEntity.ok(quizService.getSubmissionForQuiz(studentId, quizId));
    }

    // ---------- Analysis & Status ----------

    @GetMapping("/{quizId}/analysis")
    public ResponseEntity<QuizAnalysisResponseDTO> getQuizAnalysis(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizAnalysisByQuiz(quizId));
    }

    @GetMapping("/{quizId}/status")
    public ResponseEntity<QuizStatusDTO> getQuizStatus(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizStatus(quizId));
    }
}
