package ms_descargas.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms_descargas.model.Descarga;
import ms_descargas.repository.DescargaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Genera descargas de prueba con DataFaker. Los IDs de usuario y contenido
 * son numeros plausibles dentro del rango que sembran ms-registro y
 * ms-catalogo respectivamente (no hay integridad referencial real entre
 * microservicios, cada uno tiene su propia base de datos).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private static final int TOTAL_DESCARGAS_ESPERADAS = 15;

    private final DescargaRepository descargaRepository;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        long existentes = descargaRepository.count();

        if (existentes >= TOTAL_DESCARGAS_ESPERADAS) {
            log.info("DataLoader: ya existen {} descargas, se omite la generacion.", existentes);
            return;
        }

        int porGenerar = (int) (TOTAL_DESCARGAS_ESPERADAS - existentes);
        log.info("DataLoader: generando {} descargas de prueba con DataFaker...", porGenerar);

        for (int i = 0; i < porGenerar; i++) {
            Descarga descarga = new Descarga(
                    null,
                    (long) faker.number().numberBetween(1, 16),
                    (long) faker.number().numberBetween(1, 21),
                    faker.options().option("COMPLETADA", "PROCESANDO", "ERROR"),
                    faker.options().option("480p", "720p", "1080p", "4K"),
                    null // se autocompleta en @PrePersist
            );

            descargaRepository.save(descarga);
        }

        log.info("DataLoader: carga de descargas de prueba completada.");
    }
}
