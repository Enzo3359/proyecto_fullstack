package com.busqueda.busqueda.client;

import com.busqueda.busqueda.dto.ContenidoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class BusquedaClient {

    private final RestClient restClient;

    public BusquedaClient(@Value("${ms.catalogo.url}") String catalogoUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(catalogoUrl)
                .build();
    }

    public List<ContenidoDTO> obtenerTodosLosContenidos() {
        ContenidoDTO[] contenidos = restClient.get()
                .uri("/api/v1/contenidos")
                .retrieve()
                .body(ContenidoDTO[].class);

        return contenidos != null ? Arrays.asList(contenidos) : List.of();
    }
}