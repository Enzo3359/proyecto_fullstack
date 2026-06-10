package ms_descargas.service;

import ms_descargas.client.AuthClient;
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


    public DescargaResponseDTO registrarDescarga(Long usuarioId, Long contenidoId, String calidad) {


        try {
            log.info("Validando existencia del usuario {} en módulo de descargas", usuarioId);
            UsuarioResponseDTO usuario = authClient.obtenerUsuario(usuarioId);
            if (usuario == null) {
                throw new RecursoNoEncontradoException("El usuario con ID " + usuarioId + " no existe.");
            }
        } catch (Exception e) {
            throw new RecursoNoEncontradoException("No se pudo validar el usuario " + usuarioId + " o no existe.");
        }


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