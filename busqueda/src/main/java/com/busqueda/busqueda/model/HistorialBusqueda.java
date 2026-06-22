package com.busqueda.busqueda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistorialBusqueda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String termino;

    @Column
    private String tipoContenido;

    @Column
    private Long generoId;

    @Column
    private LocalDateTime fechaBusqueda;

    @Column
    private Integer resultadosEncontrados;

    @PrePersist
    protected void onCreate() {
        this.fechaBusqueda = LocalDateTime.now();
    }
}