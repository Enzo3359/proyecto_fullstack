package com.busqueda.busqueda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialBusquedaResponseDTO {

    private Long id;

    private String termino;

    private String tipoContenido;

    private Long generoId;

    private LocalDateTime fechaBusqueda;

    private Integer resultadosEncontrados;
}