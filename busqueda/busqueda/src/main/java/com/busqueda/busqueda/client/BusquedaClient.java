package com.busqueda.busqueda.client;

import com.busqueda.busqueda.dto.ContenidoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Arrays;
import java.util.List;

@Component
public class BusquedaClient {

    private final RestClient restClient;

    public BusquedaClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8081")
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



