package com.busqueda.busqueda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
        private Long id;
        private String nombre;
        private String email;
        private LocalDate fechaRegistro;
        private Boolean activo;
}
