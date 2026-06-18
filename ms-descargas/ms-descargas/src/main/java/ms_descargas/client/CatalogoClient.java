package ms_descargas.client; // O ms_reproduccion.client dependiendo de cuál estés usando

import ms_descargas.dto.ContenidoResponseDTO; // Asegúrate de crear este DTO o cambiar la ruta
import org.springframework.cloud.openfeign.FeignClient; // <--- ESTO LE QUITA EL ROJO A @FeignClient
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", url = "${ms.catalogo.url}")
public interface CatalogoClient {

    @GetMapping("/api/v1/contenidos/{id}")
    ContenidoResponseDTO obtenerContenido(@PathVariable Long id);
}