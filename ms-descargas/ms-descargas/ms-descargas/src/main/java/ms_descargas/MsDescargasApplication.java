package ms_descargas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsDescargasApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsDescargasApplication.class, args);
	}
}
