package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "LEGO_SETS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = {"idSet", "prodId", "setName"})
public class LegoSet {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "legoSetSeq")
    @SequenceGenerator(name = "legoSetSeq", sequenceName = "SEQ_LEGO_SET_ID", allocationSize = 1)
    @Column(name = "ID_SET")
    private Integer idSet;

    @Column(name = "PROD_ID", nullable = false)
    private Integer prodId;

    @Column(name = "SET_NAME", nullable = false, length = 200)
    private String setName;

    @Column(name = "PROD_DESC", length = 2048)
    private String prodDesc;

    // En el DDL es VARCHAR(32). Si luego querés enum, se puede mapear con @Enumerated.
    @Column(name = "REVIEW_DIFFICULTY", length = 32)
    private String reviewDifficulty;

    @Column(name = "PIECE_COUNT")
    private Integer pieceCount;

    // DECIMAL(3,1)
    @Column(name = "STAR_RATING", precision = 3, scale = 1)
    private BigDecimal starRating;

    // DECIMAL(10,2)
    @Column(name = "LIST_PRICE", precision = 10, scale = 2)
    private BigDecimal listPrice;

    // --- Relaciones (FKs) ---
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "THEME_ID", nullable = false)
    private Theme theme;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "AGE_GROUP_ID", nullable = false)
    private AgeGroup ageGroup;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    public void setNombre(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setNombre'");
    }
}
