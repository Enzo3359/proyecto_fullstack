package com.durocplus.registroUsuario.service;

import com.durocplus.registroUsuario.client.SuscripcionClient;
import com.durocplus.registroUsuario.dto.*;
import com.durocplus.registroUsuario.exception.RecursoNoEncontradoException;
import com.durocplus.registroUsuario.model.Usuario;
import com.durocplus.registroUsuario.repository.UsuarioRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final SuscripcionClient suscripcionClient;

    private UsuarioResponseDTO mapToDTO(Usuario u) {
        return new UsuarioResponseDTO(
                u.getId(), u.getNombre(), u.getEmail(), u.getFechaRegistro(), u.getActivo()
        );
    }

    public AuthResponseDTO registrar(RegistroRequestDTO dto) {
        log.info("Registrando usuario: {}", dto.getEmail());

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + dto.getEmail());
        }

        Usuario nuevo = new Usuario(
                null,
                dto.getNombre(),
                dto.getEmail(),
                dto.getContraseña(),
                LocalDate.now(),
                true
        );

        usuarioRepository.save(nuevo);
        log.info("Usuario registrado con id {}", nuevo.getId());

        return new AuthResponseDTO(nuevo.getEmail(), nuevo.getNombre(), "Registro exitoso");
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        log.info("Intento de login: {}", dto.getEmail());

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado: " + dto.getEmail()));

        if (!usuario.getActivo()) {
            throw new RuntimeException("La cuenta está desactivada");
        }

        if (!dto.getContraseña().equals(usuario.getContraseña())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        log.info("Login exitoso: {}", dto.getEmail());
        return new AuthResponseDTO(usuario.getEmail(), usuario.getNombre(), "Login exitoso");
    }

    public SuscripcionResponseDTO obtenerSuscripcion(Long usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado: " + usuarioId));
        try {
            return suscripcionClient.obtenerSuscripcionPorUsuario(usuarioId);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(
                    "El usuario " + usuarioId + " no tiene suscripción activa");
        } catch (FeignException e) {
            throw new RuntimeException("No se puede conectar con ms-suscripcion: " + e.getMessage());
        }
    }

    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return usuarioRepository.findById(id).map(this::mapToDTO);
    }

    public Optional<UsuarioResponseDTO> actualizar(Long id, RegistroRequestDTO dto) {
        return usuarioRepository.findById(id).map(existente -> {
            log.info("Actualizando usuario id {}", id);
            existente.setNombre(dto.getNombre());
            existente.setEmail(dto.getEmail());
            existente.setContraseña(dto.getContraseña());
            return mapToDTO(usuarioRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        log.warn("Eliminando usuario id {}", id);
        usuarioRepository.deleteById(id);
    }



}