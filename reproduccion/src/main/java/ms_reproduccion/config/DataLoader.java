package ms_reproduccion.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms_reproduccion.model.Reproduccion;
import ms_reproduccion.repository.ReproduccionRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Genera progresos de reproduccion de prueba con DataFaker. Los IDs de
 * usuario y contenido son numeros plausibles dentro del rango sembrado por
 * ms-registro y ms-catalogo (cada microservicio tiene su propia base).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private static final int TOTAL_REPRODUCCIONES_ESPERADAS = 15;

    private final ReproduccionRepository reproduccionRepository;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        long existentes = reproduccionRepository.count();

        if (existentes >= TOTAL_REPRODUCCIONES_ESPERADAS) {
            log.info("DataLoader: ya existen {} reproducciones, se omite la generacion.", existentes);
            return;
        }

        int porGenerar = (int) (TOTAL_REPRODUCCIONES_ESPERADAS - existentes);
        log.info("DataLoader: generando {} reproducciones de prueba con DataFaker...", porGenerar);

        for (int i = 0; i < porGenerar; i++) {
            Reproduccion reproduccion = new Reproduccion(
                    null,
                    (long) faker.number().numberBetween(1, 16),
                    (long) faker.number().numberBetween(1, 21),
                    faker.number().numberBetween(0, 7200),
                    faker.options().option(true, false),
                    null // se autocompleta en @PrePersist / @PreUpdate
            );

            reproduccionRepository.save(reproduccion);
        }

        log.info("DataLoader: carga de reproducciones de prueba completada.");
    }
}
