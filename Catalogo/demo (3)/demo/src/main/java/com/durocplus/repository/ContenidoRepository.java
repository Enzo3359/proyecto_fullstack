package com.durocplus.repository;


import com.durocplus.model.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {
    List<Contenido> findByTipo(String tipo);
    List<Contenido> findByDisponibleTrue();
    List<Contenido> findByGeneroId(Long generoId);

}
