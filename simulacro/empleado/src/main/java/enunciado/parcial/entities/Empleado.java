package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleado")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"departamento", "puesto"})
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    // DECIMAL(10,2)
    @Column(name = "salario", nullable = false)
    private double salario;

    @Column(name = "empleado_fijo", nullable = false)
    private boolean empleadoFijo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "departamento_id",
        foreignKey = @ForeignKey(name = "fk_departamento")
    )
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "puesto_id",
        foreignKey = @ForeignKey(name = "fk_puesto")
    )
    private Puesto puesto;

    /**
     * Devuelve el salario original o con 8% extra si el empleado es fijo.
     */
    public double calcularSalarioFinal() {
    return empleadoFijo ? salario * 1.08 : salario;
}

}
