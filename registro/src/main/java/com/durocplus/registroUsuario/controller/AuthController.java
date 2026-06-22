package com.durocplus.registroUsuario.controller;

import com.durocplus.registroUsuario.dto.*;
import com.durocplus.registroUsuario.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(authService.obtenerTodos());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable Long id) {
        return authService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/actualizar/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RegistroRequestDTO dto) {
        return authService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        authService.eliminar(id);
        return ResponseEntity.ok("usuario eliminado correctamente");
    }

    @GetMapping("/usuarios/{id}/suscripcion")
    public ResponseEntity<SuscripcionResponseDTO> obtenerSuscripcion(@PathVariable Long id) {
        return ResponseEntity.ok(authService.obtenerSuscripcion(id));
    }
}
