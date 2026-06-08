package ms_descargas.client;

import ms_descargas.dto.ContenidoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", url = "${ms.catalogo.url}")
public interface CatalogoClient {

    @GetMapping("/api/v1/contenidos/{id}")
    ContenidoResponseDTO obtenerContenido(@PathVariable Long id);
}
