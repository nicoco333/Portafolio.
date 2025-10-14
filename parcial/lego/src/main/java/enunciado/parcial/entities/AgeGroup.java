package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AGE_GROUPS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = {"idAgeGroup", "code"})
public class AgeGroup {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ageGroupSeq")
    @SequenceGenerator(name = "ageGroupSeq", sequenceName = "SEQ_AGE_GROUP_ID", allocationSize = 1)
    @Column(name = "ID_AGE_GROUP")
    private Integer idAgeGroup;

    @Column(name = "CODE", nullable = false, length = 16, unique = true)
    private String code;

    // Valores derivados del campo 'code'. No se persisten; se calculan a partir del literal.
    @Transient
    private Integer minAge;

    @Transient
    private Integer maxAge;

    /**
     * Setter personalizado para calcular bounds cuando se asigna el code.
     */
    public void setCode(String code) {
        this.code = code;
        deriveBoundsFromCode();
    }

    @PostLoad
    public void postLoad() {
        deriveBoundsFromCode();
    }

    private void deriveBoundsFromCode() {
        this.minAge = null;
        this.maxAge = null;
        if (this.code == null) return;
        String s = this.code.trim();

        try {
            if (s.endsWith("+")) {
                // formato '13+' -> min=13, max=null
                String n = s.substring(0, s.length() - 1).trim();
                this.minAge = Integer.parseInt(n);
                this.maxAge = null;
                return;
            }

            if (s.contains("-")) {
                String[] parts = s.split("-", 2);
                String a = parts[0].trim();
                String b = parts[1].trim();
                this.minAge = Integer.parseInt(a);
                this.maxAge = Integer.parseInt(b);
                return;
            }

            // formato simple '12' -> exact value
            this.minAge = Integer.parseInt(s);
            this.maxAge = this.minAge;
        } catch (NumberFormatException ex) {
            // Si el literal no es reconocible, dejar min/max nulos
            this.minAge = null;
            this.maxAge = null;
        }
    }

    /**
     * Retorna true si el age pertenece al rango representado por este AgeGroup.
     * - Si maxAge == null => age >= minAge
     * - Si minAge == maxAge => igualdad exacta
     * - En caso contrario => minAge <= age <= maxAge
     */
    public boolean matchesAge(int age) {
        if (this.minAge == null) return false; // no se puede determinar
        if (this.maxAge == null) return age >= this.minAge;
        if (this.minAge.equals(this.maxAge)) return age == this.minAge;
        return age >= this.minAge && age <= this.maxAge;
    }

}
