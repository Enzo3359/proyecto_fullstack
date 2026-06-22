package com.busqueda.busqueda.config;

import com.busqueda.busqueda.model.HistorialBusqueda;
import com.busqueda.busqueda.repository.HistorialBusquedaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Genera historial de busquedas de prueba con DataFaker para poder probar
 * los endpoints sin depender de busquedas manuales previas. Idempotente.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private static final int TOTAL_HISTORIAL_ESPERADO = 15;

    private final HistorialBusquedaRepository historialBusquedaRepository;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        long existentes = historialBusquedaRepository.count();

        if (existentes >= TOTAL_HISTORIAL_ESPERADO) {
            log.info("DataLoader: ya existen {} registros de historial, se omite la generacion.", existentes);
            return;
        }

        int porGenerar = (int) (TOTAL_HISTORIAL_ESPERADO - existentes);
        log.info("DataLoader: generando {} registros de historial de busqueda con DataFaker...", porGenerar);

        for (int i = 0; i < porGenerar; i++) {
            String tipoContenido = faker.options().option("PELICULA", "SERIE", "DOCUMENTAL");
            String termino = "PELICULA".equals(tipoContenido) ? faker.movie().name() : faker.book().title();

            // Coincide con los generos sembrados en ms-catalogo (ids 1 al 6)
            Long generoId = (long) faker.number().numberBetween(1, 7);

            HistorialBusqueda historial = new HistorialBusqueda(
                    null,
                    termino,
                    tipoContenido,
                    generoId,
                    null, // se autocompleta en @PrePersist
                    faker.number().numberBetween(0, 25)
            );

            historialBusquedaRepository.save(historial);
        }

        log.info("DataLoader: carga de historial de busqueda completada.");
    }
}
