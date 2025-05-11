package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateQuizRequestDTO {

    private Integer grade;
    private String subject;
    private String topic;
    private Timestamp deadline;

}
