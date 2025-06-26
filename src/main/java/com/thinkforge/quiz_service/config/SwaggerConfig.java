package com.thinkforge.quiz_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Quiz Service API")
                        .description("API documentation for the Quiz Microservice")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Arghya Banerjee")
                                .email("arghya.banerjee.dev@gmail.com")
                        )
                );
    }
}
