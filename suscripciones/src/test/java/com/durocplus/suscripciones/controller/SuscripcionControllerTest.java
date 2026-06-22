package com.durocplus.suscripciones.controller;

import com.durocplus.suscripciones.dto.SuscripcionRequestDTO;
import com.durocplus.suscripciones.dto.SuscripcionResponseDTO;
import com.durocplus.suscripciones.service.SuscripcionService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SuscripcionController.class)
public class SuscripcionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SuscripcionService suscripcionService;

    @Autowired
    private ObjectMapper objectMapper;

    private SuscripcionResponseDTO suscripcionDTO;
    private SuscripcionRequestDTO suscripcionRequestDTO;

    @BeforeEach
    void setUp() {
        suscripcionDTO = new SuscripcionResponseDTO(
                1L, 1L, "PREMIUM", LocalDate.now(), LocalDate.now().plusMonths(1), true);

        suscripcionRequestDTO = new SuscripcionRequestDTO(1L, "PREMIUM", LocalDate.now().plusMonths(1));
    }

    @Test
    public void testCrear() throws Exception {
        when(suscripcionService.crear(any(SuscripcionRequestDTO.class))).thenReturn(suscripcionDTO);

        mockMvc.perform(post("/api/suscripciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(suscripcionRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.plan").value("PREMIUM"));
    }

    @Test
    public void testObtenerTodas() throws Exception {
        when(suscripcionService.obtenerTodas()).thenReturn(List.of(suscripcionDTO));

        mockMvc.perform(get("/api/suscripciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testObtenerPorId() throws Exception {
        when(suscripcionService.obtenerPorId(1L)).thenReturn(suscripcionDTO);

        mockMvc.perform(get("/api/suscripciones/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plan").value("PREMIUM"));
    }

    @Test
    public void testObtenerPorUsuario() throws Exception {
        when(suscripcionService.obtenerPorUsuario(1L)).thenReturn(List.of(suscripcionDTO));

        mockMvc.perform(get("/api/suscripciones/usuario/{usuarioId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(1));
    }

    @Test
    public void testCancelar() throws Exception {
        SuscripcionResponseDTO cancelada = new SuscripcionResponseDTO(
                1L, 1L, "PREMIUM", LocalDate.now(), LocalDate.now().plusMonths(1), false);

        when(suscripcionService.cancelar(eq(1L))).thenReturn(cancelada);

        mockMvc.perform(patch("/api/suscripciones/{id}/cancelar", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activa").value(false));
    }
}
