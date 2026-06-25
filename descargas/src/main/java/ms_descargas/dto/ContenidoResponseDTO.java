package ms_descargas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContenidoResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String tipo;
    private Integer duracionMin;
    private Integer anioEstreno;
    private String clasificacion;
    private Boolean disponible;
    private String generoNombre;
}