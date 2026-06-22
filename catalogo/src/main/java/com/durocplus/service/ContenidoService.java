package com.durocplus.service;

import com.durocplus.dto.ContenidoRequestDTO;
import com.durocplus.dto.ContenidoResponseDTO;
import com.durocplus.exception.RecursoNoEncontradoException;
import com.durocplus.model.Contenido;
import com.durocplus.model.Genero;
import com.durocplus.repository.ContenidoRepository;
import com.durocplus.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ContenidoService {

    private static final Logger log = LoggerFactory.getLogger(ContenidoService.class);

    private final ContenidoRepository contenidoRepository;
    private final GeneroRepository generoRepository;

    private ContenidoResponseDTO mapToDTO(Contenido c) {
        return new ContenidoResponseDTO(
                c.getId(),
                c.getTitulo(),
                c.getDescripcion(),
                c.getTipo(),
                c.getDuracionMin(),
                c.getAnioEstreno(),
                c.getClasificacion(),
                c.getDisponible(),
                c.getGenero().getNombre()
        );
    }

    public List<ContenidoResponseDTO> obtenertodos(){
        return contenidoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ContenidoResponseDTO> obtenerdisponibles(){
        return contenidoRepository.findByDisponibleTrue().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ContenidoResponseDTO> obtenerporid(long id){
        return contenidoRepository.findById(id).map(this::mapToDTO);
    }

    public List<ContenidoResponseDTO> obtenerportipos(List<String> tipos){
        List<String> tiposLower = tipos.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        List<ContenidoResponseDTO> resultado = contenidoRepository.findByTipoIn(tiposLower).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No se encontraron contenidos para el/los tipo(s): " + tipos);
        }

        return resultado;
    }

    public ContenidoResponseDTO guardar (ContenidoRequestDTO dto){
        log.info("Creando contenido: {}", dto.getTitulo());

        Genero genero = generoRepository.findById(dto.getGeneroId())
                .orElseThrow(()-> new RecursoNoEncontradoException
                        ("genero no encontrado: "+dto.getGeneroId()));

        Contenido nuevo = new Contenido(
                null,
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getTipo().toUpperCase(),
                dto.getDuracionMin(),
                dto.getAnioEstreno(),
                dto.getClasificacion(),
                dto.getDisponible(),
                genero
        );
        Contenido guardado = contenidoRepository.save(nuevo);
        log.info("contenido guardado con la id {}", guardado.getId());
        return mapToDTO(guardado);
    }
    public void eliminar(Long id){

        if (!contenidoRepository.existsById(id)) {
            throw new RuntimeException("Contenido no encontrado con id: " + id);
        }

        contenidoRepository.deleteById(id);
    }



}
