package ms_reproduccion.repository;


import ms_reproduccion.model.Reproduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReproduccionRepository extends JpaRepository<Reproduccion, Long> {

    Optional<Reproduccion> findByUsuarioIdAndContenidoId(Long usuarioId, Long contenidoId);
}