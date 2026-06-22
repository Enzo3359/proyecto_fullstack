package com.durocplus.suscripciones.service;

import com.durocplus.suscripciones.client.UsuarioClient;
import com.durocplus.suscripciones.dto.SuscripcionRequestDTO;
import com.durocplus.suscripciones.dto.SuscripcionResponseDTO;
import com.durocplus.suscripciones.exception.RecursoNoEncontradoException;
import com.durocplus.suscripciones.model.Suscripcion;
import com.durocplus.suscripciones.repository.SuscripcionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuscripcionServiceTest {

    @Mock
    private SuscripcionRepository suscripcionRepository;

    @Mock
    private UsuarioClient usuarioClient;

    private SuscripcionService suscripcionService;

    @BeforeEach
    void configurar() {
        suscripcionService = new SuscripcionService(
                suscripcionRepository,
                usuarioClient
        );
    }

    @Test
    void debeObtenerTodasLasSuscripciones() {
        Suscripcion suscripcion = crearSuscripcionActiva();

        when(suscripcionRepository.findAll())
                .thenReturn(List.of(suscripcion));

        List<SuscripcionResponseDTO> resultado =
                suscripcionService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("PREMIUM", resultado.get(0).getPlan());
        assertTrue(resultado.get(0).getActiva());

        verify(suscripcionRepository, times(1)).findAll();
    }

    @Test
    void debeObtenerSuscripcionPorId() {
        Suscripcion suscripcion = crearSuscripcionActiva();

        when(suscripcionRepository.findById(1L))
                .thenReturn(Optional.of(suscripcion));

        SuscripcionResponseDTO resultado =
                suscripcionService.obtenerPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals(7L, resultado.getUsuarioId());
        assertEquals("PREMIUM", resultado.getPlan());

        verify(suscripcionRepository, times(1)).findById(1L);
    }

    @Test
    void debeCancelarSuscripcion() {
        Suscripcion suscripcion = crearSuscripcionActiva();

        when(suscripcionRepository.findById(1L))
                .thenReturn(Optional.of(suscripcion));
        when(suscripcionRepository.save(any(Suscripcion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SuscripcionResponseDTO resultado =
                suscripcionService.cancelar(1L);

        assertFalse(resultado.getActiva());
        verify(suscripcionRepository, times(1)).save(suscripcion);
    }

    @Test
    void debeCrearSuscripcionParaUsuarioExistente() {
        LocalDate vencimiento = LocalDate.now().plusMonths(1);
        SuscripcionRequestDTO request = new SuscripcionRequestDTO(
                7L,
                "PREMIUM",
                vencimiento
        );

        when(usuarioClient.obtenerUsuario(7L))
                .thenReturn(new Object());
        when(suscripcionRepository.save(any(Suscripcion.class)))
                .thenAnswer(invocation -> {
                    Suscripcion guardada = invocation.getArgument(0);
                    guardada.setId(1L);
                    guardada.setFechaInicio(LocalDate.now());
                    return guardada;
                });

        SuscripcionResponseDTO resultado =
                suscripcionService.crear(request);

        assertEquals(1L, resultado.getId());
        assertEquals(7L, resultado.getUsuarioId());
        assertEquals("PREMIUM", resultado.getPlan());
        assertEquals(vencimiento, resultado.getFechaVencimiento());
        assertTrue(resultado.getActiva());

        verify(usuarioClient, times(1)).obtenerUsuario(7L);
        verify(suscripcionRepository, times(1)).save(any(Suscripcion.class));
    }

    @Test
    void debeRechazarCreacionCuandoUsuarioNoExiste() {
        SuscripcionRequestDTO request = new SuscripcionRequestDTO(
                99L,
                "BASICO",
                LocalDate.now().plusMonths(1)
        );

        when(usuarioClient.obtenerUsuario(99L))
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> suscripcionService.crear(request)
        );

        verify(suscripcionRepository, never()).save(any(Suscripcion.class));
    }

    private Suscripcion crearSuscripcionActiva() {
        return new Suscripcion(
                1L,
                7L,
                "PREMIUM",
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                true
        );
    }
}
