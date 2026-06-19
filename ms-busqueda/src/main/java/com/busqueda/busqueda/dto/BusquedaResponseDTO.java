package com.busqueda.busqueda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaResponseDTO {

    private String terminoBuscado;


    private int totalResultados;


    private List<ContenidoDTO> resultados;
}