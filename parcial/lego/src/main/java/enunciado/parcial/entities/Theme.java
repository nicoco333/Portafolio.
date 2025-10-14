package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "THEMES")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = {"idTheme", "name"})
public class Theme {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "themeSeq")
    @SequenceGenerator(name = "themeSeq", sequenceName = "SEQ_THEME_ID", allocationSize = 1)
    @Column(name = "ID_THEME")
    private Integer idTheme;

    @Column(name = "NAME", nullable = false, length = 120, unique = true)
    private String name;
}
