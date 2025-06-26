package com.thinkforge.quiz_service.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkforge.quiz_service.dto.GeneratedQuestionsDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

@Service
public class QuestionService {

    List<GeneratedQuestionsDTO> generateQuizQuestion(String grade, String subject, String topic, int numQuestions) {

        List<GeneratedQuestionsDTO> questions = null;

        try {
            String scriptPath = Paths.get("generate.py").toString();

            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    scriptPath,
                    grade, subject, topic, String.valueOf(numQuestions)
            );

            pb.redirectErrorStream(true);
            pb.directory(new java.io.File("."));

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                String json = jsonBuilder.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                questions = objectMapper.readValue(json, new TypeReference<>() {});
            } else {
                System.err.println("Python script failed with exit code: " + exitCode);
                System.err.println("Script output:\n" + jsonBuilder.toString());
                throw new RuntimeException("Python script failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to call Python script: ", e);
        }

        return questions;
    }
}
