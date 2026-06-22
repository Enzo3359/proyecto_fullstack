package com.durocplus.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.TrueFalseConverter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContenidoRequestDTO {

    @NotBlank(message = "ingrese titulo")
    @Size(max = 200)
    private String titulo;

    private String descripcion;

    @NotBlank(message = "ingrese tipo")
    @Pattern(regexp = "PELICULA|SERIE|DOCUMENTAL", message = "el tipo debe ser PELICULA, SERIE o DOCUMENTAL")
    private String tipo;

    @NotNull(message = "la duraci0n es obligatoria")
    @Min(value = 1,message = "duracion minima de un minuto")
    private Integer duracionMin;

    @NotNull(message = "el año de estreno es obligatorio")
    @Min(value = 1900)@Max(2100)
    private Integer anioEstreno;

    @NotBlank(message = "la clasificacion es obligatoria")
    private String clasificacion;

    private Boolean disponible = true;

    @NotNull(message = "el id del genero es obligatorio")
    private Long generoId;



}
