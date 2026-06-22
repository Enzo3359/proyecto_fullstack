package com.busqueda.busqueda.controller;

import com.busqueda.busqueda.dto.BusquedaRequestDTO;
import com.busqueda.busqueda.dto.BusquedaResponseDTO;
import com.busqueda.busqueda.dto.HistorialBusquedaResponseDTO;
import com.busqueda.busqueda.service.BusquedaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BusquedaController.class)
public class BusquedaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BusquedaService busquedaService;

    @Autowired
    private ObjectMapper objectMapper;

    private HistorialBusquedaResponseDTO historialDTO;
    private BusquedaRequestDTO busquedaRequestDTO;

    @BeforeEach
    void setUp() {
        historialDTO = new HistorialBusquedaResponseDTO(
                1L, "matrix", "PELICULA", 5L, LocalDateTime.now(), 3);

        busquedaRequestDTO = new BusquedaRequestDTO("matrix", "PELICULA", 5L);
    }

    @Test
    public void testObtenerTodos() throws Exception {
        when(busquedaService.obtenerTodos()).thenReturn(List.of(historialDTO));

        mockMvc.perform(get("/api/busquedas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].termino").value("matrix"));
    }

    @Test
    public void testObtenerPorId() throws Exception {
        when(busquedaService.obtenerPorId(1L)).thenReturn(Optional.of(historialDTO));

        mockMvc.perform(get("/api/busquedas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.termino").value("matrix"));
    }

    @Test
    public void testObtenerPorIdNoEncontrado() throws Exception {
        when(busquedaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/busquedas/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBuscar() throws Exception {
        BusquedaResponseDTO respuesta = new BusquedaResponseDTO("matrix", 1, List.of());
        when(busquedaService.buscar(any(BusquedaRequestDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/busquedas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(busquedaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.terminoBuscado").value("matrix"));
    }

    @Test
    public void testEliminar() throws Exception {
        when(busquedaService.obtenerPorId(1L)).thenReturn(Optional.of(historialDTO));

        mockMvc.perform(delete("/api/busquedas/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(busquedaService).eliminar(eq(1L));
    }

    @Test
    public void testEliminarNoEncontrado() throws Exception {
        when(busquedaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/busquedas/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}
