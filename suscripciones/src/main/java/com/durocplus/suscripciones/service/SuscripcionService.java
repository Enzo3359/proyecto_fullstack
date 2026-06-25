package com.durocplus.suscripciones.service;

import com.durocplus.suscripciones.client.UsuarioClient;
import com.durocplus.suscripciones.dto.SuscripcionRequestDTO;
import com.durocplus.suscripciones.dto.SuscripcionResponseDTO;
import com.durocplus.suscripciones.dto.UsuarioResponseDTO;
import com.durocplus.suscripciones.exception.RecursoNoEncontradoException;
import com.durocplus.suscripciones.model.Suscripcion;
import com.durocplus.suscripciones.repository.SuscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final UsuarioClient usuarioClient;

    public SuscripcionResponseDTO crear(SuscripcionRequestDTO dto) {

        try {
            UsuarioResponseDTO usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId());
            if (usuario == null) {
                throw new RuntimeException(
                        "El usuario " + dto.getUsuarioId() + " no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "No se pudo validar el usuario con ms-registro: " + e.getMessage());
        }

        Suscripcion nueva = new Suscripcion(
                null,
                dto.getUsuarioId(),
                dto.getPlan(),
                null,
                dto.getFechaVencimiento(),
                true
        );
        return mapearADTO(suscripcionRepository.save(nueva));
    }

    public List<SuscripcionResponseDTO> obtenerTodas() {
        return suscripcionRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public SuscripcionResponseDTO obtenerPorId(Long id) {
        return mapearADTO(suscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Suscripción no encontrada con id: " + id)));
    }

    public List<SuscripcionResponseDTO> obtenerPorUsuario(Long usuarioId) {
        return suscripcionRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public SuscripcionResponseDTO cancelar(Long id) {
        Suscripcion suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Suscripción no encontrada con id: " + id));
        suscripcion.setActiva(false);
        return mapearADTO(suscripcionRepository.save(suscripcion));
    }

    private SuscripcionResponseDTO mapearADTO(Suscripcion s) {
        return new SuscripcionResponseDTO(
                s.getId(),
                s.getUsuarioId(),
                s.getPlan(),
                s.getFechaInicio(),
                s.getFechaVencimiento(),
                s.getActiva()
        );
    }
}