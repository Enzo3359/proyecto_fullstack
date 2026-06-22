package com.durocplus.registroUsuario.config;

import com.durocplus.registroUsuario.model.Usuario;
import com.durocplus.registroUsuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Genera usuarios de prueba adicionales con DataFaker, por encima de los
 * usuarios base que ya inserta Flyway (V2). Idempotente entre reinicios.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class DataLoader implements CommandLineRunner {

    private static final int TOTAL_USUARIOS_ESPERADOS = 15;

    private final UsuarioRepository usuarioRepository;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        long existentes = usuarioRepository.count();

        if (existentes >= TOTAL_USUARIOS_ESPERADOS) {
            log.info("DataLoader: ya existen {} usuarios, se omite la generacion de datos falsos.", existentes);
            return;
        }

        int porGenerar = (int) (TOTAL_USUARIOS_ESPERADOS - existentes);
        log.info("DataLoader: generando {} usuarios de prueba con DataFaker...", porGenerar);

        int creados = 0;
        int intentos = 0;

        while (creados < porGenerar && intentos < porGenerar * 5) {
            intentos++;

            String email = faker.internet().emailAddress();

            if (usuarioRepository.existsByEmail(email)) {
                continue; // evita choques con el constraint unique de email
            }

            Usuario usuario = new Usuario(
                    null,
                    faker.name().fullName(),
                    email,
                    faker.internet().password(8, 16),
                    LocalDate.now().minusDays(faker.number().numberBetween(1, 500)),
                    faker.options().option(true, true, true, false)
            );

            usuarioRepository.save(usuario);
            creados++;
        }

        log.info("DataLoader: {} usuarios de prueba creados.", creados);
    }
}
