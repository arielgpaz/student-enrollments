package com.apaz.studentenrollments.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openApi() {
        var openAPI = new OpenAPI();
        openAPI.setComponents(new Components());
        openAPI.info(new Info().title("API de Alunos e Matriculas")
                .description("API de cadastro de alunos, cursos e matriculas")
                .version("1.0.0"));

        return openAPI;
    }
}
