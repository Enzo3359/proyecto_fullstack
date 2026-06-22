package ms_descargas.config; // O ms_descargas.config

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // <--- Esto le dice a Spring que es un archivo de configuración
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        // Esto le dice a Feign que imprima en la consola todo el detalle
        // de las peticiones que le haces a tus amigos (URLs, Headers, respuestas)
        return Logger.Level.FULL;
    }
}