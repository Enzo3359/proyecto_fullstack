package ms_descargas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "descargas")
public class Descarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "contenido_id", nullable = false)
    private Long contenidoId;

    @Column(nullable = false, length = 20)
    private String estado; // Ejemplo: "PROCESANDO", "COMPLETADA"

    @Column(length = 10)
    private String calidad; // Ejemplo: "720p", "1080p", "4K"

    @Column(name = "fecha_descarga")
    private LocalDateTime fechaDescarga;

    @PrePersist
    protected void onCreate() {
        this.fechaDescarga = LocalDateTime.now();
    }
}