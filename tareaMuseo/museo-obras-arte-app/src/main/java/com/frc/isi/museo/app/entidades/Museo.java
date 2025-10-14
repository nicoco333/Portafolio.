package com.frc.isi.museo.app.entidades;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedQueries({
    @NamedQuery(name = "Museo.GetAll", query = "SELECT a FROM Museo a"),
    @NamedQuery(name = "Museo.GetByNombre", query = "SELECT m FROM Museo m WHERE m.nombre = :nombre"),    
})

public class Museo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    private String nombre;

    @OneToMany(mappedBy = "museo", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ObraArtistica> obras = new HashSet<>();
    public void addObra(ObraArtistica obra) {
        this.obras.add(obra);
        obra.setMuseo(this);
    }

}
