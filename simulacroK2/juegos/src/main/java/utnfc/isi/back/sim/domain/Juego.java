package utnfc.isi.back.sim.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "JUEGOS")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class Juego {
    @Id
    @SequenceGenerator(name = "seq_juego", sequenceName = "SEQ_JUEGOS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_juego")
    @Column(name = "JUEGO_ID")
    private int id;

    @Column(name = "TITULO", nullable = false, length = 255)
    private String titulo;

    @Column(name = "FECHA_LANZAMIENTO")
    private Integer fechaLanzamiento; 

    @ManyToOne(optional = true)
    @JoinColumn(name = "GENERO_ID")
    private Genero genero;

    @ManyToOne(optional = true)
    @JoinColumn(name = "DESARROLLADOR_ID")
    private Desarrollador desarrollador;

    @ManyToOne(optional = true)
    @JoinColumn(name = "PLATAFORMA_ID")
    private Plataforma plataforma;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "JUEGOS_FINALIZADOS")
    private Integer juegosFinalizados;

    @Column(name = "JUGANDO")
    private Integer jugando;

    @Lob
    @Column(name = "RESUMEN", nullable = false)
    private String resumen;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "CLASIFICACION_ESRB", length = 4)
    private String clasificacionEsrbCode;

    @Transient
    public ClasificacionEsrb getClasificacionEsrb() {
        return clasificacionEsrbCode == null ? null : ClasificacionEsrb.fromCodigo(clasificacionEsrbCode);
    }

    public void setClasificacionEsrb(ClasificacionEsrb esrb) {
        this.clasificacionEsrbCode = (esrb == null ? null : esrb.getCodigo());
    }

    public void setClasificacionEsrbCode(String code) {
        this.clasificacionEsrbCode = (code == null || code.isBlank()) ? null : code.trim();
    }
}
