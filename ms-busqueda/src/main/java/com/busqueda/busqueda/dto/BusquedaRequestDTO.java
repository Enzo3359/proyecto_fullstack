package com.busqueda.busqueda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaRequestDTO {

    @NotBlank(message = "El término de búsqueda no puede estar vacío")
    @Size(min = 2, max = 100, message = "El término debe tener entre 2 y 100 caracteres")
    private String termino;


    @Pattern(regexp = "^(PELICULA|SERIE|DOCUMENTAL)?$",
            message = "El tipo debe ser PELICULA, SERIE o DOCUMENTAL")
    private String tipo;


    @Positive(message = "El ID de género debe ser un número positivo")
    private Long generoId;
}