package com.frc.isi.museo.app.entidades;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQueries({
    @NamedQuery(name = "EstiloArtistico.getAllWithObras", query = "SELECT e FROM EstiloArtistico e LEFT JOIN FETCH e.obras"),    
})

public class EstiloArtistico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    private String nombre;
    
    @OneToMany(mappedBy = "estiloArtistico", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ObraArtistica> obras = new HashSet<>();
    public void addObra(ObraArtistica obra) {
        this.obras.add(obra);
        obra.setEstiloArtistico(this);
    }  
}
