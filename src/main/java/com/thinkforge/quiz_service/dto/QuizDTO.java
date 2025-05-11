package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class QuizDTO {
    private UUID quizId;
    private UUID createdBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String subject;
    private String topic;
    private int grade;
    private Timestamp deadline;
}
