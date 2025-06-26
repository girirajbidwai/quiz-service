package com.thinkforge.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private int status;
}
