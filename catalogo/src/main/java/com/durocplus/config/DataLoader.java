package com.durocplus.config;

import com.durocplus.model.Contenido;
import com.durocplus.model.Genero;
import com.durocplus.repository.ContenidoRepository;
import com.durocplus.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Genera contenido de prueba adicional usando DataFaker, por encima de los
 * datos base que ya inserta Flyway (V3/V4) y/o el DataInitializer. Es
 * idempotente: si ya existe suficiente contenido, no vuelve a insertar en
 * cada reinicio.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class DataLoader implements CommandLineRunner {

    private static final int TOTAL_CONTENIDOS_ESPERADOS = 20;

    private final ContenidoRepository contenidoRepository;
    private final GeneroRepository generoRepository;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        long existentes = contenidoRepository.count();

        if (existentes >= TOTAL_CONTENIDOS_ESPERADOS) {
            log.info("DataLoader: ya existen {} contenidos, se omite la generacion de datos falsos.", existentes);
            return;
        }

        List<Genero> generos = generoRepository.findAll();

        if (generos.isEmpty()) {
            log.warn("DataLoader: no hay generos cargados (revisa las migraciones de Flyway). Se omite la carga.");
            return;
        }

        int porGenerar = (int) (TOTAL_CONTENIDOS_ESPERADOS - existentes);
        log.info("DataLoader: generando {} contenidos de prueba con DataFaker...", porGenerar);

        for (int i = 0; i < porGenerar; i++) {
            Genero generoAleatorio = generos.get(faker.number().numberBetween(0, generos.size()));

            String tipo = faker.options().option("PELICULA", "SERIE", "DOCUMENTAL");
            String titulo = "PELICULA".equals(tipo) ? faker.lorem().sentence(3) : faker.book().title();

            Contenido contenido = new Contenido(
                    null,
                    titulo,
                    faker.lorem().sentence(15),
                    tipo,
                    faker.number().numberBetween(18, 180),
                    faker.number().numberBetween(1980, 2026),
                    faker.options().option("ATP", "+13", "+16", "+18"),
                    faker.options().option(true, true, true, false),
                    generoAleatorio
            );

            contenidoRepository.save(contenido);
        }

        log.info("DataLoader: carga de contenidos de prueba completada.");
    }
}
