package com.durocplus.suscripciones.config;

import com.durocplus.suscripciones.model.Suscripcion;
import com.durocplus.suscripciones.repository.SuscripcionRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Locale;

@Configuration
public class DataFakerConfig {

    @Bean
    @ConditionalOnProperty(
            name = "app.datafaker.enabled",
            havingValue = "true"
    )
    public CommandLineRunner cargarDatosFalsos(
            SuscripcionRepository repository) {

        return args -> {
            if (repository.count() > 0) {
                return;
            }

            Faker faker = new Faker(Locale.of("es"));
            String[] planes = {"BASICO", "ESTANDAR", "PREMIUM"};

            for (int i = 0; i < 10; i++) {
                Suscripcion suscripcion = new Suscripcion();
                suscripcion.setUsuarioId(faker.number().numberBetween(1L, 21L));
                suscripcion.setPlan(faker.options().option(planes));
                suscripcion.setFechaVencimiento(
                        LocalDate.now().plusDays(
                                faker.number().numberBetween(30, 366)
                        )
                );
                suscripcion.setActiva(true);

                repository.save(suscripcion);
            }
        };
    }
}
