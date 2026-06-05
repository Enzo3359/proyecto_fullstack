package com.durocplus.service;


import com.durocplus.model.Genero;
import com.durocplus.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GeneroService {


    private static final Logger log = LoggerFactory.getLogger(GeneroService.class);

    private final GeneroRepository generorespository;

    public List<Genero> obtenertodos(){
        log.debug("obteniendo todos los generos");
        return generorespository.findAll();
    }

    public Optional<Genero> obtenerporid(long id){
        return generorespository.findById(id);
    }

    public Genero guardar(Genero genero){
        return generorespository.save(genero);
    }

    public Optional<Genero> actualizar(long id, Genero generoactualizacion){
        return generorespository.findById(id).map(existente->{
            existente.setNombre(generoactualizacion.getNombre());
            existente.setDescripcion(generoactualizacion.getDescripcion());
            return generorespository.save(existente);
        });
    }

    public void eliminar(long id){
        log.warn("eliminando genero por id", id);
        generorespository.deleteById(id);
    }
}
