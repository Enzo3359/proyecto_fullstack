package com.busqueda.busqueda.service;

import com.busqueda.busqueda.client.BusquedaClient;
import com.busqueda.busqueda.dto.HistorialBusquedaResponseDTO;
import com.busqueda.busqueda.model.HistorialBusqueda;
import com.busqueda.busqueda.repository.HistorialBusquedaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusquedaServiceTest {

    @Mock
    private HistorialBusquedaRepository historialRepository;

    @Mock
    private BusquedaClient catalogoClient;

    private BusquedaService busquedaService;

    @BeforeEach
    void configurar() {
        busquedaService = new BusquedaService(
                historialRepository,
                catalogoClient
        );
    }

    @Test
    void debeObtenerTodoElHistorial() {

        HistorialBusqueda historial = new HistorialBusqueda(
                1L,
                "Batman",
                "PELICULA",
                2L,
                LocalDateTime.now(),
                5
        );

        when(historialRepository.findAll())
                .thenReturn(List.of(historial));

        List<HistorialBusquedaResponseDTO> resultado =
                busquedaService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Batman", resultado.get(0).getTermino());
        assertEquals("PELICULA", resultado.get(0).getTipoContenido());
        assertEquals(5, resultado.get(0).getResultadosEncontrados());

        verify(historialRepository, times(1)).findAll();
    }

    @Test
    void debeObtenerHistorialPorId() {

        HistorialBusqueda historial = new HistorialBusqueda(
                1L,
                "Matrix",
                "PELICULA",
                3L,
                LocalDateTime.now(),
                8
        );

        when(historialRepository.findById(1L))
                .thenReturn(Optional.of(historial));

        Optional<HistorialBusquedaResponseDTO> resultado =
                busquedaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Matrix", resultado.get().getTermino());
        assertEquals(8, resultado.get().getResultadosEncontrados());

        verify(historialRepository, times(1)).findById(1L);
    }

    @Test
    void debeRetornarVacioCuandoNoExisteElId() {

        when(historialRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<HistorialBusquedaResponseDTO> resultado =
                busquedaService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());

        verify(historialRepository, times(1)).findById(99L);
    }

    @Test
    void debeEliminarHistorialPorId() {

        busquedaService.eliminar(1L);

        verify(historialRepository, times(1))
                .deleteById(1L);
    }
}