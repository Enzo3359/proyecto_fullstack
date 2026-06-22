package ms_descargas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescargaResponseDTO {
    private Long id;
    private Long usuarioId;
    private Long contenidoId;
    private String estado;
    private String calidad;
    private LocalDateTime fechaDescarga;
}