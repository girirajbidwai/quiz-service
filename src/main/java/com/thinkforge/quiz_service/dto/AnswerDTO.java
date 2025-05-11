package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerDTO {

    private UUID questionId;
    private String selectedOption;
}
