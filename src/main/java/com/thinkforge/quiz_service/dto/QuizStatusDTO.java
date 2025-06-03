package com.thinkforge.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizStatusDTO {

    private UUID quizId;
    private boolean isActive;
    private int totalSubmissions;
    private int maxScore;
    private float averageScore;
}
