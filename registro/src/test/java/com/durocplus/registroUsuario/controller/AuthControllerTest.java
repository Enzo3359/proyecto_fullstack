package com.durocplus.registroUsuario.controller;

import com.durocplus.registroUsuario.dto.AuthResponseDTO;
import com.durocplus.registroUsuario.dto.LoginRequestDTO;
import com.durocplus.registroUsuario.dto.RegistroRequestDTO;
import com.durocplus.registroUsuario.dto.SuscripcionResponseDTO;
import com.durocplus.registroUsuario.dto.UsuarioResponseDTO;
import com.durocplus.registroUsuario.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioResponseDTO usuarioDTO;
    private RegistroRequestDTO registroRequestDTO;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioResponseDTO(
                1L, "Juan Perez", "juan@gmail.com", LocalDate.of(2024, 3, 15), true);

        registroRequestDTO = new RegistroRequestDTO("Juan Perez", "juan@gmail.com", "clave123");
        loginRequestDTO = new LoginRequestDTO("juan@gmail.com", "clave123");
    }

    @Test
    public void testRegistrar() throws Exception {
        AuthResponseDTO respuesta = new AuthResponseDTO("juan@gmail.com", "Juan Perez", "Registro exitoso");
        when(authService.registrar(any(RegistroRequestDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/auth/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje").value("Registro exitoso"));
    }

    @Test
    public void testLogin() throws Exception {
        AuthResponseDTO respuesta = new AuthResponseDTO("juan@gmail.com", "Juan Perez", "Login exitoso");
        when(authService.login(any(LoginRequestDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Login exitoso"));
    }

    @Test
    public void testListarUsuarios() throws Exception {
        when(authService.obtenerTodos()).thenReturn(List.of(usuarioDTO));

        mockMvc.perform(get("/api/v1/auth/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("juan@gmail.com"));
    }

    @Test
    public void testObtenerUsuarioPorId() throws Exception {
        when(authService.obtenerPorId(1L)).thenReturn(Optional.of(usuarioDTO));

        mockMvc.perform(get("/api/v1/auth/usuarios/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    public void testObtenerUsuarioPorIdNoEncontrado() throws Exception {
        when(authService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/auth/usuarios/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testActualizarUsuario() throws Exception {
        when(authService.actualizar(eq(1L), any(RegistroRequestDTO.class)))
                .thenReturn(Optional.of(usuarioDTO));

        // BUG ORIGINAL en el controller: @PutMapping("actualizar/usuarios/{id}") sin "/" inicial
        mockMvc.perform(put("/api/v1/auth/actualizar/usuarios/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@gmail.com"));
    }

    @Test
    public void testEliminarUsuario() throws Exception {
        mockMvc.perform(delete("/api/v1/auth/usuarios/{id}", 1L))
                .andExpect(status().isOk());

        verify(authService).eliminar(eq(1L));
    }

    @Test
    public void testObtenerSuscripcion() throws Exception {
        SuscripcionResponseDTO suscripcion = new SuscripcionResponseDTO(
                1L, 1L, "PREMIUM", LocalDate.now(), LocalDate.now().plusMonths(1), true);

        when(authService.obtenerSuscripcion(1L)).thenReturn(suscripcion);

        mockMvc.perform(get("/api/v1/auth/usuarios/{id}/suscripcion", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plan").value("PREMIUM"));
    }
}
