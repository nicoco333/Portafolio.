package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COUNTRIES")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = {"idCountry", "code", "name"})
public class Country {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countrySeq")
    @SequenceGenerator(name = "countrySeq", sequenceName = "SEQ_COUNTRY_ID", allocationSize = 1)
    @Column(name = "ID_COUNTRY")
    private Integer idCountry;

    @Column(name = "CODE", nullable = false, length = 3, unique = true)
    private String code;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
}
