package ms_reproduccion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReproduccionResponseDTO {
    private Long id;
    private Long usuarioId;
    private Long contenidoId;
    private Integer segundoActual;
    private Boolean completado;
    private LocalDateTime ultimaVez;
}