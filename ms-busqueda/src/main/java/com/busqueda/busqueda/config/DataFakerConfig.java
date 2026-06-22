package com.busqueda.busqueda.config;

import com.busqueda.busqueda.model.HistorialBusqueda;
import com.busqueda.busqueda.repository.HistorialBusquedaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataFakerConfig {

    @Bean
    @ConditionalOnProperty(
            name = "app.datafaker.enabled",
            havingValue = "true"
    )
    public CommandLineRunner cargarDatosFalsos(
            HistorialBusquedaRepository repository) {

        return args -> {

            if (repository.count() > 0) {
                return;
            }

            Faker faker = new Faker(Locale.of("es"));

            String[] tiposContenido = {
                    "PELICULA",
                    "SERIE",
                    "DOCUMENTAL"
            };

            for (int i = 0; i < 10; i++) {

                HistorialBusqueda historial = new HistorialBusqueda();

                historial.setTermino(
                        faker.book().title()
                );

                historial.setTipoContenido(
                        faker.options().option(tiposContenido)
                );

                historial.setGeneroId(
                        faker.number().numberBetween(1L, 10L)
                );

                historial.setResultadosEncontrados(
                        faker.number().numberBetween(1, 50)
                );

                repository.save(historial);
            }
        };
    }
}