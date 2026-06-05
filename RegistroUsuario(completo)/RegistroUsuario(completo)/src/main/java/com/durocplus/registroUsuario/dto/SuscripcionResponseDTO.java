package com.durocplus.registroUsuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuscripcionResponseDTO {
    private Long id;
    private Long usuarioId;
    private String plan;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Boolean activa;
}
