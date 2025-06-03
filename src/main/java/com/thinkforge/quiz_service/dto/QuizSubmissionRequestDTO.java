package com.thinkforge.quiz_service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuizSubmissionRequestDTO {

    private UUID studentId;
    private List<AnswerDTO> answers;
}
