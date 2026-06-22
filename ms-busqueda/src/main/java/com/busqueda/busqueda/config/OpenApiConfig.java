package com.busqueda.busqueda.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI busquedaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Búsqueda - DurocPlus")
                        .description("Documentación del microservicio de búsqueda de DurocPlus")
                        .version("1.0.0"));
    }
}