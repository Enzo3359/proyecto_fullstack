package com.durocplus.controller;

import com.durocplus.model.Genero;
import com.durocplus.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
public class GeneroController {
    private static final Logger log = LoggerFactory.getLogger(GeneroController.class);

    private final GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<Genero>> listarTodos() {

        log.info("Listando todos los géneros");

        return ResponseEntity.ok(generoService.obtenertodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> obtenerPorId(@PathVariable Long id) {

        log.info("Buscando género con id {}", id);

        return generoService.obtenerporid(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Genero> guardar(
            @Valid @RequestBody Genero genero) {

        log.info("Guardando género");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generoService.guardar(genero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Genero genero) {

        log.info("Actualizando género con id {}", id);

        return generoService.actualizar(id, genero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        log.warn("Eliminando género con id {}", id);

        generoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
