package ms_reproduccion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Importante

@SpringBootApplication
@EnableFeignClients
public class MsReproduccionDescargasApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsReproduccionDescargasApplication.class, args);
	}
}