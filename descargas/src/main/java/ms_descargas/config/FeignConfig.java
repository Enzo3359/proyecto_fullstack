package ms_descargas.config; // O ms_descargas.config

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // <--- Esto le dice a Spring que es un archivo de configuración
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {

        return Logger.Level.FULL;
    }
}