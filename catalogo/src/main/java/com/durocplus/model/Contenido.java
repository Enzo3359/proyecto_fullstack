package com.durocplus.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contenido")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(name = "duracion_min")
    private Integer duracionMin;

    @Column(name = "anio_estreno")
    private Integer anioEstreno;

    @Column(nullable = false, length = 10)
    private String clasificacion;

    @Column(nullable = false)
    private Boolean disponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id")
    @JsonBackReference
    private Genero genero;
}
