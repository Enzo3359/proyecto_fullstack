package com.durocplus.suscripciones.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuscripcionRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El plan es obligatorio")
    @Pattern(regexp = "BASICO|ESTANDAR|PREMIUM", message = "El plan debe ser BASICO, ESTANDAR o PREMIUM")
    private String plan;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;
}