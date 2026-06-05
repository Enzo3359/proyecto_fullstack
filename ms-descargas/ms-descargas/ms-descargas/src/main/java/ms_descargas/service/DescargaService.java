package ms_descargas.service;

import ms_descargas.client.AuthClient;
import ms_descargas.client.CatalogoClient;
import ms_descargas.dto.ContenidoResponseDTO;
import ms_descargas.dto.DescargaResponseDTO;
import ms_descargas.dto.UsuarioResponseDTO;
import ms_descargas.exception.RecursoNoEncontradoException;
import ms_descargas.model.Descarga;
import ms_descargas.repository.DescargaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DescargaService {

    private final DescargaRepository descargaRepository;
    private final AuthClient authClient;
    private final CatalogoClient catalogoclient;


    public DescargaResponseDTO registrarDescarga(Long usuarioId, Long contenidoId, String calidad) {

        try {
            // 1. NUEVO: Validar primero si el usuario existe con Enzo
            UsuarioResponseDTO usuario = authClient.obtenerUsuario(usuarioId);
            if (usuario == null) {
                throw new RecursoNoEncontradoException("El usuario no existe en el sistema.");
            }

            // 2. Lo que ya tenías: Validar el contenido
            ContenidoResponseDTO contenido = catalogoclient.obtenerContenido(contenidoId);
            if (contenido == null) {
                throw new RecursoNoEncontradoException("El contenido no existe en el catálogo.");
            }

        } catch (Exception e) {
            log.error("ERROR REAL EN LA COMUNICACIÓN CON ENZO (Auth/Catalogo): ", e);
            throw new RecursoNoEncontradoException("Error en la conexión o validación: " + e.getMessage());
        }

        // Si ambos existen, pasa para acá y guarda la descarga
        Descarga descarga = new Descarga(null, usuarioId, contenidoId, "COMPLETADA", calidad, null);
        Descarga guardada = descargaRepository.save(descarga);

        return convertirADto(guardada);
    }

    public List<DescargaResponseDTO> obtenerDescargasPorUsuario(Long usuarioId) {
        return descargaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    private DescargaResponseDTO convertirADto(Descarga descarga) {
        return new DescargaResponseDTO(
                descarga.getId(),
                descarga.getUsuarioId(),
                descarga.getContenidoId(),
                descarga.getEstado(),
                descarga.getCalidad(),
                descarga.getFechaDescarga()
        );
    }
}