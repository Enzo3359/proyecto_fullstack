package com.durocplus.suscripciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "suscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del usuario que se suscribió
    @Column(nullable = false)
    private Long usuarioId;

    // Plan: BASICO, ESTANDAR, PREMIUM
    @Column(nullable = false, length = 20)
    private String plan;

    // Cuándo empieza y cuándo vence
    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    // Si la suscripción está activa o no
    @Column(nullable = false)
    private Boolean activa = true;

    // Se pone la fecha de inicio automáticamente al crear
    @PrePersist
    protected void onCreate() {
        this.fechaInicio = LocalDate.now();
    }
}