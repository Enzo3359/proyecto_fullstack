package ms_descargas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms_descargas.dto.DescargaResponseDTO;
import ms_descargas.service.DescargaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DescargaController.class)
public class DescargaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DescargaService descargaService;

    @Autowired
    private ObjectMapper objectMapper;

    private DescargaResponseDTO descargaDTO;

    @BeforeEach
    void setUp() {
        descargaDTO = new DescargaResponseDTO(
                1L, 1L, 1L, "COMPLETADA", "1080p", LocalDateTime.now());
    }

    @Test
    public void testSolicitarDescarga() throws Exception {
        when(descargaService.registrarDescarga(eq(1L), eq(1L), eq("1080p")))
                .thenReturn(descargaDTO);

        mockMvc.perform(post("/api/v1/descargas/solicitar")
                        .param("usuarioId", "1")
                        .param("contenidoId", "1")
                        .param("calidad", "1080p")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("COMPLETADA"))
                .andExpect(jsonPath("$.calidad").value("1080p"));
    }

    @Test
    public void testListarPorUsuario() throws Exception {
        when(descargaService.obtenerDescargasPorUsuario(1L)).thenReturn(List.of(descargaDTO));

        mockMvc.perform(get("/api/v1/descargas/usuario/{usuarioId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].usuarioId").value(1));
    }
}
