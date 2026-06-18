package ms_descargas.dto; // O ms_reproduccion.dto

import lombok.Data;

@Data
public class ContenidoResponseDTO {
    private Long id;
    private String titulo;
    // Puedes dejarlo solo con el ID y el título por ahora para que compile limpito
}