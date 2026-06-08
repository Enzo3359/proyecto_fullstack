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
    private final CatalogoClient catalogoClient;

    public DescargaResponseDTO registrarDescarga(Long usuarioId, Long contenidoId, String calidad) {

        try {
            log.info("Validando usuario {} con ms-registroUsuario", usuarioId);
            UsuarioResponseDTO usuario = authClient.obtenerUsuario(usuarioId);
            if (usuario == null) {
                throw new RecursoNoEncontradoException("El usuario con ID " + usuarioId + " no existe.");
            }
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RecursoNoEncontradoException("No se pudo validar el usuario " + usuarioId + ": " + e.getMessage());
        }

        try {
            log.info("Validando contenido {} con ms-catalogo", contenidoId);
            ContenidoResponseDTO contenido = catalogoClient.obtenerContenido(contenidoId);
            if (contenido == null) {
                throw new RecursoNoEncontradoException("El contenido con ID " + contenidoId + " no existe.");
            }
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RecursoNoEncontradoException("No se pudo validar el contenido " + contenidoId + ": " + e.getMessage());
        }

        Descarga descarga = new Descarga(null, usuarioId, contenidoId, "COMPLETADA", calidad, null);
        return convertirADto(descargaRepository.save(descarga));
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