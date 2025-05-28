package com.thinkforge.quiz_service.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkforge.quiz_service.dto.GetQuestionsDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class QuestionService {

    List<GetQuestionsDTO> generateQuizQuestion(String grade, String subject, String topic, int numQuestions) {

        List<GetQuestionsDTO> questions = null;

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "d:\\SpringBoot\\quiz-service\\src\\main\\java\\com\\thinkforge\\quiz_service\\external_utils\\.venv\\Scripts\\python.exe",
                    "d:\\SpringBoot\\quiz-service\\src\\main\\java\\com\\thinkforge\\quiz_service\\external_utils\\generate.py",
                    grade, subject, topic, String.valueOf(numQuestions)
            );

            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            int exitCode =  process.waitFor();
            if(exitCode == 0) {
                String json = jsonBuilder.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                questions = objectMapper.readValue(json, new TypeReference<>() {});
            }
            else {
                System.err.println("Python script failed with exit code: " + exitCode);
                System.err.println("Script output:\n" + jsonBuilder.toString());  // Print what Python printed
                throw new RuntimeException("Python script failed with exit code: " + exitCode);

            }
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to call Python script: ", e);
        }

        return questions;

    }

}
