package ms_descargas;

import ms_descargas.client.AuthClient;
import ms_descargas.dto.DescargaResponseDTO;
import ms_descargas.dto.UsuarioResponseDTO;
import ms_descargas.model.Descarga;
import ms_descargas.repository.DescargaRepository;
import ms_descargas.service.DescargaService;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DescargaServiceTest {

    @Mock
    private DescargaRepository descargaRepository;

    @Mock
    private AuthClient authClient; // Mockeamos el Feign Client para simular la validación de usuario

    @InjectMocks
    private DescargaService descargaService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.faker = new Faker();
    }

    @Test
    void registrarDescargaExitosamenteTest() {
        // 1. ARRANGE (Datos de entrada falsos creados con DataFaker)
        Long usuarioId = faker.number().numberBetween(1L, 200L);
        Long contenidoId = faker.number().numberBetween(1L, 500L);
        String calidad = faker.options().option("720p", "1080p", "4K");

        // Simular respuesta exitosa del microservicio de Autenticación usando setters
        UsuarioResponseDTO usuarioMock = new UsuarioResponseDTO();
        usuarioMock.setId(usuarioId);
        usuarioMock.setNombre(faker.name().username());
        usuarioMock.setEmail(faker.internet().emailAddress());

        when(authClient.obtenerUsuario(usuarioId)).thenReturn(usuarioMock);

        // Simular comportamiento del repositorio al guardar
        Descarga descargaGuardadaEnBD = new Descarga(10L, usuarioId, contenidoId, "COMPLETADA", calidad, LocalDateTime.now());
        when(descargaRepository.save(any(Descarga.class))).thenReturn(descargaGuardadaEnBD);

        // 2. ACT (Ejecutar tu método real)
        DescargaResponseDTO resultado = descargaService.registrarDescarga(usuarioId, contenidoId, calidad);

        // 3. ASSERT (Validaciones de JUnit 5)
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals(usuarioId, resultado.getUsuarioId());
        assertEquals("COMPLETADA", resultado.getEstado());
        assertEquals(calidad, resultado.getCalidad());

        // Verificar que los mocks fueron llamados adecuadamente
        verify(authClient, times(1)).obtenerUsuario(usuarioId);
        verify(descargaRepository, times(1)).save(any(Descarga.class));
    }

    @Test
    void obtenerDescargasPorUsuarioTest() {
        // 1. ARRANGE
        Long usuarioId = faker.number().numberBetween(1L, 100L);
        Descarga descargaMock = new Descarga(1L, usuarioId, 50L, "COMPLETADA", "1080p", LocalDateTime.now());

        // Cuando busquen por usuario, devolvemos una lista con nuestra descarga mock
        when(descargaRepository.findByUsuarioId(usuarioId)).thenReturn(Collections.singletonList(descargaMock));

        // 2. ACT
        List<DescargaResponseDTO> resultado = descargaService.obtenerDescargasPorUsuario(usuarioId);

        // 3. ASSERT
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(usuarioId, resultado.get(0).getUsuarioId());

        verify(descargaRepository, times(1)).findByUsuarioId(usuarioId);
    }
}