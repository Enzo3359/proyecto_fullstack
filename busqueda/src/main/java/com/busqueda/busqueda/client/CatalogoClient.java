package com.busqueda.busqueda.client;

import com.busqueda.busqueda.dto.ContenidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-catalogo", url = "${ms.catalogo.url}")
public interface CatalogoClient {

    @GetMapping("/api/v1/contenidos")
    List<ContenidoDTO> obtenerTodosLosContenidos();
}
