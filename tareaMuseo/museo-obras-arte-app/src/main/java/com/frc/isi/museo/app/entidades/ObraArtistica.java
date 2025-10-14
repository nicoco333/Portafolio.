package com.frc.isi.museo.app.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedQueries({
    @NamedQuery(name = "ObraArtistica.GetById", query = "SELECT o FROM ObraArtistica o WHERE o.codigo = :codigo"),
    @NamedQuery(name = "ObraArtistica.GetByNombre", query = "SELECT o FROM ObraArtistica o WHERE o.nombre = :nombre"),    
})

public class ObraArtistica {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    private String nombre;
    private int anio;
    private double montoAsegurado;
    private boolean seguroTotal;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "museoId", referencedColumnName = "id")
    private Museo museo;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "autorId", referencedColumnName = "id")
    private Autor autor;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "estiloArtisticoId", referencedColumnName = "id")
    private EstiloArtistico estiloArtistico;
}
