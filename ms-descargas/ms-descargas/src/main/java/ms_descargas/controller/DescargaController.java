package ms_descargas.controller;

import ms_descargas.dto.DescargaResponseDTO;
import ms_descargas.service.DescargaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/descargas")
@RequiredArgsConstructor
public class DescargaController {

    private final DescargaService descargaService;

    @PostMapping("/solicitar")
    public ResponseEntity<DescargaResponseDTO> solicitarDescarga(
            @RequestParam Long usuarioId,
            @RequestParam Long contenidoId,
            @RequestParam String calidad) {

        log.info("Iniciando descarga para usuario {} del contenido {}", usuarioId, contenidoId);
        DescargaResponseDTO response = descargaService.registrarDescarga(usuarioId, contenidoId, calidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DescargaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        log.info("Buscando historial de descargas del usuario {}", usuarioId);
        return ResponseEntity.ok(descargaService.obtenerDescargasPorUsuario(usuarioId));
    }
}