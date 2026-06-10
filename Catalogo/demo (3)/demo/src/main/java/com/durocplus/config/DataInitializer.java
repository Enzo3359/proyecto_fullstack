package com.durocplus.config;



import com.durocplus.model.Genero;
import com.durocplus.model.Contenido;
import com.durocplus.repository.GeneroRepository;
import com.durocplus.repository.ContenidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final GeneroRepository generoRepository;
    private final ContenidoRepository contenidoRepository;

    @Override
    public void run(String... args) {

        if (generoRepository.count() > 0) {
            log.info("Datos ya cargados. Se omite inicialización.");
            return;
        }

        log.info("Cargando datos iniciales...");

        Genero accion    = generoRepository.save(new Genero(null, "Acción",          "Películas de acción", null));
        Genero drama     = generoRepository.save(new Genero(null, "Drama",           "Narrativas emocionales", null));
        Genero comedia   = generoRepository.save(new Genero(null, "Comedia",         "Entretenimiento humorístico", null));
        Genero scifi     = generoRepository.save(new Genero(null, "Ciencia Ficción", "Mundos futuros", null));

        contenidoRepository.save(new Contenido(null, "Origen",       "Un ladrón roba secretos del subconsciente.", "PELICULA", 148, 2010, "PG-13", true, scifi));
        contenidoRepository.save(new Contenido(null, "Interestelar", "Astronautas viajan más allá de nuestra galaxia.", "PELICULA", 169, 2014, "PG-13", true, scifi));
        contenidoRepository.save(new Contenido(null, "Breaking Bad", "Un profesor de química se convierte en criminal.", "SERIE", 60, 2008, "R", true, drama));
        contenidoRepository.save(new Contenido(null, "The Office",   "La vida cotidiana de una oficina.", "SERIE", 22, 2005, "PG-13", true, comedia));
        contenidoRepository.save(new Contenido(null, "Avengers",     "Los superhéroes más poderosos se unen.", "PELICULA", 143, 2012, "PG-13", true, accion));

        log.info(">>> Datos iniciales cargados OK.");
    }
}