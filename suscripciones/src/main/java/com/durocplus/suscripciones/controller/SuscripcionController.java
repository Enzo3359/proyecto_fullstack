package com.durocplus.suscripciones.controller;

import com.durocplus.suscripciones.dto.SuscripcionRequestDTO;
import com.durocplus.suscripciones.dto.SuscripcionResponseDTO;
import com.durocplus.suscripciones.service.SuscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    @PostMapping
    public ResponseEntity<SuscripcionResponseDTO> crear(
            @Valid @RequestBody SuscripcionRequestDTO dto) {
        return ResponseEntity.status(201).body(suscripcionService.crear(dto));
    }


    @GetMapping
    public ResponseEntity<List<SuscripcionResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(suscripcionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(suscripcionService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SuscripcionResponseDTO>> obtenerPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(suscripcionService.obtenerPorUsuario(usuarioId));
    }


    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<SuscripcionResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(suscripcionService.cancelar(id));
    }
}