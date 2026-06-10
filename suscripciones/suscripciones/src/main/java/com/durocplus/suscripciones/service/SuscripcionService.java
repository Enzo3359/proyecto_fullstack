package com.durocplus.suscripciones.service;

import com.durocplus.suscripciones.dto.SuscripcionRequestDTO;
import com.durocplus.suscripciones.dto.SuscripcionResponseDTO;
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

    // Crea una nueva suscripción
    public SuscripcionResponseDTO crear(SuscripcionRequestDTO dto) {
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

    // Devuelve todas las suscripciones
    public List<SuscripcionResponseDTO> obtenerTodas() {
        return suscripcionRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    // Devuelve una suscripción por id
    public SuscripcionResponseDTO obtenerPorId(Long id) {
        return mapearADTO(suscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Suscripción no encontrada con id: " + id)));
    }

    // Devuelve todas las suscripciones de un usuario
    public List<SuscripcionResponseDTO> obtenerPorUsuario(Long usuarioId) {
        return suscripcionRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    // Cancela una suscripción (la marca como inactiva)
    public SuscripcionResponseDTO cancelar(Long id) {
        Suscripcion suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Suscripción no encontrada con id: " + id));
        suscripcion.setActiva(false);
        return mapearADTO(suscripcionRepository.save(suscripcion));
    }

    // Convierte la entidad al DTO de respuesta
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