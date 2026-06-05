package ms_reproduccion.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reproducciones")
public class Reproduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "contenido_id", nullable = false)
    private Long contenidoId;

    @Column(name = "segundo_actual", nullable = false)
    private Integer segundoActual;

    @Column(name = "completado", nullable = false)
    private Boolean completado = false;

    @Column(name = "ultima_vez")
    private LocalDateTime ultimaVez;


    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.ultimaVez = LocalDateTime.now();
    }
}