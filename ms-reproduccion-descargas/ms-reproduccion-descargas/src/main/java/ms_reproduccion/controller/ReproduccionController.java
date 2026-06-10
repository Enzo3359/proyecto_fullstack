package ms_reproduccion.controller;

import ms_reproduccion.dto.ReproduccionResponseDTO;
import ms_reproduccion.service.ReproduccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reproducciones")
@RequiredArgsConstructor
public class ReproduccionController {

    private final ReproduccionService reproduccionService;


    @PostMapping("/guardar")
    public ResponseEntity<ReproduccionResponseDTO> guardarProgreso(
            @RequestParam Long usuarioId,
            @RequestParam Long contenidoId,
            @RequestParam Integer segundoActual,
            @RequestParam(defaultValue = "false") Boolean completado) {

        log.info("Guardando progreso para Usuario {}, Contenido {}, Segundo {}", usuarioId, contenidoId, segundoActual);
        ReproduccionResponseDTO guardado = reproduccionService.guardarProgreso(usuarioId, contenidoId, segundoActual, completado);
        return ResponseEntity.ok(guardado);
    }


    @GetMapping("/historial")
    public ResponseEntity<ReproduccionResponseDTO> obtenerProgreso(
            @RequestParam Long usuarioId,
            @RequestParam Long contenidoId) {

        log.info("Buscando historial de Usuario {} y Contenido {}", usuarioId, contenidoId);
        return reproduccionService.obtenerProgreso(usuarioId, contenidoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}