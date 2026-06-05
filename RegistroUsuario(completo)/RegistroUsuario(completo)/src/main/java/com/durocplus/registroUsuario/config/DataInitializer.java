package com.durocplus.registroUsuario.config;


import org.springframework.boot.CommandLineRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("ms-auth iniciado correctamente en puerto 8082");
        log.info("Endpoints disponibles:");
        log.info("  POST /api/v1/auth/registrar");
        log.info("  POST /api/v1/auth/login");
        log.info("  GET  /api/v1/auth/usuarios");
    }
}
