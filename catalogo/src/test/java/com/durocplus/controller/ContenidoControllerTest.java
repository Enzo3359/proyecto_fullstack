package com.durocplus.controller;

import com.durocplus.dto.ContenidoRequestDTO;
import com.durocplus.dto.ContenidoResponseDTO;
import com.durocplus.service.ContenidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ContenidoController.class)
public class ContenidoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Proporciona una manera de realizar peticiones HTTP en las pruebas

    @MockitoBean
    private ContenidoService contenidoservice; // Crea un mock del servicio de Contenido

    @Autowired
    private ObjectMapper objectMapper; // Se usa para convertir objetos Java a JSON y viceversa

    private ContenidoResponseDTO contenidoDTO;
    private ContenidoRequestDTO contenidoRequestDTO;

    @BeforeEach
    void setUp() {
        contenidoDTO = new ContenidoResponseDTO();
        contenidoDTO.setId(1L);
        contenidoDTO.setTitulo("elias therian");
        contenidoDTO.setDescripcion("un therian");
        contenidoDTO.setTipo("SERIE");
        contenidoDTO.setDuracionMin(50);
        contenidoDTO.setAnioEstreno(2010);
        contenidoDTO.setClasificacion("+13");
        contenidoDTO.setDisponible(true);
        contenidoDTO.setGeneroNombre("terror");

        contenidoRequestDTO = new ContenidoRequestDTO(
                "elias therian",
                "un therian",
                "SERIE",
                50,
                2010,
                "+13",
                true,
                1L
        );
    }

    @Test
    public void testGetAllContenidos() throws Exception {
        when(contenidoservice.obtenertodos()).thenReturn(List.of(contenidoDTO));

        mockMvc.perform(get("/api/v1/contenidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("elias therian"));
    }

    @Test
    public void testGetContenidoById() throws Exception {

        when(contenidoservice.obtenerporid(1L)).thenReturn(Optional.of(contenidoDTO));

        mockMvc.perform(get("/api/v1/contenidos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("elias therian"));
    }

    @Test
    public void testGetContenidoByIdNoEncontrado() throws Exception {
        when(contenidoservice.obtenerporid(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/contenidos/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetDisponibles() throws Exception {

        when(contenidoservice.obtenerdisponibles()).thenReturn(List.of(contenidoDTO));

        mockMvc.perform(get("/api/v1/contenidos/disponibles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("elias therian"));
    }

    @Test
    public void testGetByTipo() throws Exception {
        when(contenidoservice.obtenerportipos(List.of("documental")))
                .thenReturn(List.of(contenidoDTO));

        mockMvc.perform(get("/api/v1/contenidos/tipo")
                        .param("tipos", "documental")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("elias therian"));
    }

    @Test
    public void testCreateContenido() throws Exception {

        when(contenidoservice.guardar(any(ContenidoRequestDTO.class))).thenReturn(contenidoDTO);

        mockMvc.perform(post("/api/v1/contenidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contenidoRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("elias therian"));
    }

    @Test
    public void testEliminarContenido() throws Exception {
        mockMvc.perform(delete("/api/v1/contenidos/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(contenidoservice).eliminar(eq(1L));
    }
}
