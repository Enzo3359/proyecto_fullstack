package ms_reproduccion.controller;

import ms_reproduccion.dto.ReproduccionResponseDTO;
import ms_reproduccion.service.ReproduccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReproduccionController.class)
public class ReproduccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReproduccionService reproduccionService;

    private ReproduccionResponseDTO reproduccionDTO;

    @BeforeEach
    void setUp() {
        reproduccionDTO = new ReproduccionResponseDTO(
                1L, 1L, 1L, 120, false, LocalDateTime.now());
    }

    @Test
    public void testGuardarProgreso() throws Exception {
        when(reproduccionService.guardarProgreso(eq(1L), eq(1L), eq(120), eq(false)))
                .thenReturn(reproduccionDTO);

        mockMvc.perform(post("/api/v1/reproducciones/guardar")
                        .param("usuarioId", "1")
                        .param("contenidoId", "1")
                        .param("segundoActual", "120")
                        .param("completado", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.segundoActual").value(120));
    }

    @Test
    public void testObtenerProgreso() throws Exception {
        when(reproduccionService.obtenerProgreso(1L, 1L)).thenReturn(Optional.of(reproduccionDTO));

        mockMvc.perform(get("/api/v1/reproducciones/historial")
                        .param("usuarioId", "1")
                        .param("contenidoId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenidoId").value(1));
    }

    @Test
    public void testObtenerProgresoSinHistorial() throws Exception {
        when(reproduccionService.obtenerProgreso(2L, 2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/reproducciones/historial")
                        .param("usuarioId", "2")
                        .param("contenidoId", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
