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

}
