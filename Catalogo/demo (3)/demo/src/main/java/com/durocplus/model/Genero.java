package com.durocplus.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genero_id")
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "genero", fetch = FetchType.LAZY)
    private List<Contenido> contenidos;
}
