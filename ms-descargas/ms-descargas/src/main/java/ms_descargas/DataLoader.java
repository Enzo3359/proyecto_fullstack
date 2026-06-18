package ms_descargas;

import ms_descargas.model.Descarga; // Tu entidad real
import ms_descargas.repository.DescargaRepository; // Tu repositorio real
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Date;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DescargaRepository descargaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Si la base de datos está vacía, creamos 20 descargas de prueba al iniciar
        if (descargaRepository.count() == 0) {
            for (int i = 0; i < 20; i++) {
                Descarga descarga = new Descarga();

                // Generamos IDs ficticios para usuarios y contenidos (entre 1 y 100)
                descarga.setUsuarioId(faker.number().numberBetween(1L, 100L));
                descarga.setContenidoId(faker.number().numberBetween(1L, 100L));

                // Usamos opciones aleatorias para el estado y la calidad
                descarga.setEstado(faker.options().option("PROCESANDO", "COMPLETADA", "FALLIDA"));
                descarga.setCalidad(faker.options().option("720p", "1080p", "4K"));

                // Nota: No necesitas setear fechaDescarga a mano,
                // ya que el @PrePersist de tu entidad la creará automáticamente al guardar.

                descargaRepository.save(descarga);
            }
            System.out.println("¡Base de datos de Descargas poblada con éxito con 20 registros ficticios de DataFaker!");
        }
    }
}