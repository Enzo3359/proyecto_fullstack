package com.durocplus.controller;

import com.durocplus.dto.ContenidoRequestDTO;
import com.durocplus.dto.ContenidoResponseDTO;
import com.durocplus.service.ContenidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/contenidos")
@RequiredArgsConstructor
public class ContenidoController {
    private static final Logger log = LoggerFactory.getLogger(ContenidoController.class);

    private final ContenidoService contenidoService;

    @GetMapping
    public ResponseEntity<List<ContenidoResponseDTO>> listarTodos() {

        log.info("Listando todos los contenidos");

        return ResponseEntity.ok(contenidoService.obtenertodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ContenidoResponseDTO>> listarDisponibles() {

        log.info("Listando contenidos disponibles");

        return ResponseEntity.ok(contenidoService.obtenerdisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenidoResponseDTO> obtenerPorId(@PathVariable Long id) {

        log.info("Buscando contenido con id {}", id);

        return contenidoService.obtenerporid(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ContenidoResponseDTO>> obtenerPorTipo(
            @PathVariable String tipo) {

        log.info("Buscando contenidos por tipo {}", tipo);

        return ResponseEntity.ok(contenidoService.obtenerportipo(tipo));
    }

    @PostMapping
    public ResponseEntity<ContenidoResponseDTO> guardar(
            @Valid @RequestBody ContenidoRequestDTO dto) {

        log.info("Guardando nuevo contenido");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contenidoService.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        contenidoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
