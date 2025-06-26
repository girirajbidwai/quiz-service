package com.thinkforge.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizPlatformStatsDTO {

    private long totalQuizzes;
    private long totalSubmissions;
    private long totalTeachers;
    private long totalStudents;
    private double averageQuizScore;
    private double highestAverageScore;
    private double lowestAverageScore;
}
