package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "puesto")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = {"id", "nombre"})
public class Puesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}
