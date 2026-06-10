package com.busqueda.busqueda.controller;

import com.busqueda.busqueda.dto.BusquedaRequestDTO;
import com.busqueda.busqueda.dto.BusquedaResponseDTO;
import com.busqueda.busqueda.dto.HistorialBusquedaResponseDTO;
import com.busqueda.busqueda.service.BusquedaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/busquedas")
@RequiredArgsConstructor
public class BusquedaController {

    private final BusquedaService busquedaService;

    @GetMapping
    public ResponseEntity<List<HistorialBusquedaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(busquedaService.obtenerTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<HistorialBusquedaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return busquedaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BusquedaResponseDTO> buscar(
            @Valid @RequestBody BusquedaRequestDTO dto) {
        return ResponseEntity.status(201).body(busquedaService.buscar(dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (busquedaService.obtenerPorId(id).isEmpty())
            return ResponseEntity.notFound().build();
        busquedaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}