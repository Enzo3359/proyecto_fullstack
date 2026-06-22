package com.durocplus.suscripciones.config;

import com.durocplus.suscripciones.model.Suscripcion;
import com.durocplus.suscripciones.repository.SuscripcionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Genera suscripciones de prueba con DataFaker. El usuarioId es un numero
 * plausible dentro del rango sembrado por ms-registro (cada microservicio
 * tiene su propia base de datos, no hay FK real entre ellas).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private static final int TOTAL_SUSCRIPCIONES_ESPERADAS = 15;

    private final SuscripcionRepository suscripcionRepository;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        long existentes = suscripcionRepository.count();

        if (existentes >= TOTAL_SUSCRIPCIONES_ESPERADAS) {
            log.info("DataLoader: ya existen {} suscripciones, se omite la generacion.", existentes);
            return;
        }

        int porGenerar = (int) (TOTAL_SUSCRIPCIONES_ESPERADAS - existentes);
        log.info("DataLoader: generando {} suscripciones de prueba con DataFaker...", porGenerar);

        for (int i = 0; i < porGenerar; i++) {
            Suscripcion suscripcion = new Suscripcion(
                    null,
                    (long) faker.number().numberBetween(1, 16),
                    faker.options().option("BASICO", "ESTANDAR", "PREMIUM"),
                    null, // se autocompleta en @PrePersist
                    LocalDate.now().plusMonths(faker.number().numberBetween(1, 13)),
                    faker.options().option(true, true, true, false)
            );

            suscripcionRepository.save(suscripcion);
        }

        log.info("DataLoader: carga de suscripciones de prueba completada.");
    }
}
