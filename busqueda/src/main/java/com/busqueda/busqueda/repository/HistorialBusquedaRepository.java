package com.busqueda.busqueda.repository;

import com.busqueda.busqueda.model.HistorialBusqueda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialBusquedaRepository extends JpaRepository<HistorialBusqueda, Long> {

    List<HistorialBusqueda> findByTerminoContainingIgnoreCase(String termino);

    List<HistorialBusqueda> findByTipoContenido(String tipoContenido);
}