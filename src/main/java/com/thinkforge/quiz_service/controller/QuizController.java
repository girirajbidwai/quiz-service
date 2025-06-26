package com.thinkforge.quiz_service.controller;

import com.thinkforge.quiz_service.dto.*;
import com.thinkforge.quiz_service.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get all quizzes", description = "Returns metadata for all available quizzes.")
    @ApiResponse(responseCode = "200", description = "List of quizzes retrieved successfully")
    @GetMapping
    public ResponseEntity<List<QuizMetadataDTO>> getAllQuiz() {
        return ResponseEntity.ok(quizService.getAllQuiz());
    }

    @Operation(summary = "Get quiz by ID", description = "Returns metadata for a specific quiz by its ID.")
    @ApiResponse(responseCode = "200", description = "Quiz metadata retrieved successfully")
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizMetadataDTO> getQuizById(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizByQuizId(quizId));
    }

    @Operation(summary = "Get quizzes by teacher ID", description = "Returns all quizzes created by a specific teacher.")
    @ApiResponse(responseCode = "200", description = "List of quizzes for the teacher retrieved successfully")
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<QuizMetadataDTO>> getQuizByTeacherId(@PathVariable UUID teacherId) {
        return ResponseEntity.ok(quizService.getQuizByTeacherId(teacherId));
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<QuizQuestionResponseDTO> getQuizQuestions(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizQuestions(quizId));
    }

    @Operation(summary = "Generate quiz", description = "Generates quiz questions based on input details such as subject, grade, and topic.")
    @ApiResponse(responseCode = "200", description = "Quiz generated successfully with list of questions")
    @PostMapping("/generate")
    public ResponseEntity<List<GeneratedQuestionsDTO>> generateQuiz(@Valid @RequestBody CreateQuizRequestDTO request) {
        return ResponseEntity.ok(quizService.generateQuiz(request));
    }

    @Operation(summary = "Update quiz", description = "Updates metadata of an existing quiz identified by quiz ID.")
    @ApiResponse(responseCode = "200", description = "Quiz updated successfully")
    @PutMapping("/{quizId}")
    public ResponseEntity<QuizMetadataDTO> updateQuiz(
            @PathVariable UUID quizId,
            @Valid @RequestBody UpdateQuizRequestDTO request) {
        return ResponseEntity.ok(quizService.updateQuiz(quizId, request));
    }

    @Operation(summary = "Delete quiz", description = "Deletes a quiz identified by its quiz ID.")
    @ApiResponse(responseCode = "200", description = "Quiz deleted successfully")
    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable UUID quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.ok("Quiz deleted successfully");
    }

    // ---------- Quiz Submission ----------

    @Operation(summary = "Submit quiz answers", description = "Submits answers for a specific quiz by a student.")
    @ApiResponse(responseCode = "200", description = "Quiz submitted successfully")
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<String> submitQuiz(
            @PathVariable UUID quizId,
            @Valid @RequestBody QuizSubmissionRequestDTO request) {
        quizService.submitQuiz(quizId, request);
        return ResponseEntity.ok("Quiz submitted successfully");
    }

    @Operation(summary = "Get all submissions for a quiz", description = "Retrieves all submissions for a given quiz.")
    @ApiResponse(responseCode = "200", description = "Submissions retrieved successfully")
    @GetMapping("/{quizId}/submissions")
    public ResponseEntity<List<QuizSubmissionAnalysisDTO>> getAllSubmissions(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getAllSubmissionsForQuiz(quizId));
    }

    @Operation(summary = "Get a student's submission", description = "Fetches the submission details for a specific student in a given quiz.")
    @ApiResponse(responseCode = "200", description = "Student's quiz submission retrieved successfully")
    @GetMapping("/{quizId}/submission/{studentId}")
    public ResponseEntity<StudentQuizAnalysisDTO> getStudentSubmission(
            @PathVariable UUID quizId,
            @PathVariable UUID studentId) {
        return ResponseEntity.ok(quizService.getSubmissionForQuiz(studentId, quizId));
    }

    // ---------- Analysis & Status ----------

    @Operation(summary = "Get quiz analysis", description = "Returns detailed analysis for all student submissions of a quiz.")
    @ApiResponse(responseCode = "200", description = "Quiz analysis data retrieved successfully")
    @GetMapping("/{quizId}/analysis")
    public ResponseEntity<QuizAnalysisResponseDTO> getQuizAnalysis(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizAnalysisByQuiz(quizId));
    }

    @Operation(summary = "Get quiz status", description = "Provides the status of a quiz including total submissions, max score, average score, etc.")
    @ApiResponse(responseCode = "200", description = "Quiz status retrieved successfully")
    @GetMapping("/{quizId}/status")
    public ResponseEntity<QuizStatusDTO> getQuizStatus(@PathVariable UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizStatus(quizId));
    }
}
