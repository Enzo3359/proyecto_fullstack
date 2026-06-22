package com.busqueda.busqueda.service;

import com.busqueda.busqueda.client.CatalogoClient;
import com.busqueda.busqueda.dto.BusquedaRequestDTO;
import com.busqueda.busqueda.dto.BusquedaResponseDTO;
import com.busqueda.busqueda.dto.ContenidoDTO;
import com.busqueda.busqueda.dto.HistorialBusquedaResponseDTO;
import com.busqueda.busqueda.model.HistorialBusqueda;
import com.busqueda.busqueda.repository.HistorialBusquedaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusquedaService {

    private final HistorialBusquedaRepository historialRepository;
    private final CatalogoClient catalogoClient;


    public BusquedaResponseDTO buscar(BusquedaRequestDTO dto) {


        List<ContenidoDTO> todos = catalogoClient.obtenerTodosLosContenidos();

        List<ContenidoDTO> resultados = todos.stream()
                .filter(c -> c.getTitulo() != null &&
                        c.getTitulo().toLowerCase().contains(dto.getTermino().toLowerCase()))

                .filter(c -> dto.getTipo() == null || dto.getTipo().isBlank() ||
                        c.getTipo().equalsIgnoreCase(dto.getTipo()))
                .collect(Collectors.toList());


        HistorialBusqueda historial = new HistorialBusqueda(
                null,
                dto.getTermino(),
                dto.getTipo(),
                dto.getGeneroId(),
                null,
                resultados.size()
        );
        historialRepository.save(historial);

        return new BusquedaResponseDTO(dto.getTermino(), resultados.size(), resultados);
    }


    public List<HistorialBusquedaResponseDTO> obtenerTodos() {
        return historialRepository.findAll().stream()
                .map(this::mapearAHistorialDTO)
                .collect(Collectors.toList());
    }

    public Optional<HistorialBusquedaResponseDTO> obtenerPorId(Long id) {
        return historialRepository.findById(id)
                .map(this::mapearAHistorialDTO);
    }

    // DELETE
    public void eliminar(Long id) {
        historialRepository.deleteById(id);
    }

    private HistorialBusquedaResponseDTO mapearAHistorialDTO(HistorialBusqueda entidad) {
        return new HistorialBusquedaResponseDTO(
                entidad.getId(),
                entidad.getTermino(),
                entidad.getTipoContenido(),
                entidad.getGeneroId(),
                entidad.getFechaBusqueda(),
                entidad.getResultadosEncontrados()
        );
    }
}