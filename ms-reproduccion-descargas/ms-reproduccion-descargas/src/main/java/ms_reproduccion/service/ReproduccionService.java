package ms_reproduccion.service;

import ms_reproduccion.client.AuthClient;
import ms_reproduccion.dto.ReproduccionResponseDTO;
import ms_reproduccion.dto.UsuarioResponseDTO;
import ms_reproduccion.exception.RecursoNoEncontradoException;
import ms_reproduccion.model.Reproduccion;
import ms_reproduccion.repository.ReproduccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReproduccionService {

    private final ReproduccionRepository reproduccionRepository;
    private final AuthClient authClient;


    public ReproduccionResponseDTO guardarProgreso(Long usuarioId, Long contenidoId, Integer segundoActual, Boolean completado) {


        try {
            log.info("Validando existencia del usuario {} con el microservicio de autenticación", usuarioId);
            UsuarioResponseDTO usuario = authClient.obtenerUsuario(usuarioId);
            if (usuario == null) {
                throw new RecursoNoEncontradoException("El usuario con ID " + usuarioId + " no existe en el sistema.");
            }
        } catch (Exception e) {

            throw new RecursoNoEncontradoException("No se pudo validar el usuario " + usuarioId + " o no existe.");
        }


        Optional<Reproduccion> historialExistente = reproduccionRepository.findByUsuarioIdAndContenidoId(usuarioId, contenidoId);
        Reproduccion reproduccion;

        if (historialExistente.isPresent()) {
            reproduccion = historialExistente.get();
            reproduccion.setSegundoActual(segundoActual);
            reproduccion.setCompletado(completado);
        } else {
            reproduccion = new Reproduccion(null, usuarioId, contenidoId, segundoActual, completado, null);
        }

        Reproduccion guardada = reproduccionRepository.save(reproduccion);
        return convertirADto(guardada);
    }


    public Optional<ReproduccionResponseDTO> obtenerProgreso(Long usuarioId, Long contenidoId) {
        return reproduccionRepository.findByUsuarioIdAndContenidoId(usuarioId, contenidoId)
                .map(this::convertirADto);
    }


    private ReproduccionResponseDTO convertirADto(Reproduccion reproduccion) {
        return new ReproduccionResponseDTO(
                reproduccion.getId(),
                reproduccion.getUsuarioId(),
                reproduccion.getContenidoId(),
                reproduccion.getSegundoActual(),
                reproduccion.getCompletado(),
                reproduccion.getUltimaVez()
        );
    }
}