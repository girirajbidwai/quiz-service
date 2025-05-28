package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
public class CreateQuizRequestDTO {

    private UUID teacherId;
    private Integer grade;
    private String subject;
    private String topic;
    private Integer numOfQuestions;
    private Timestamp deadline;
}
