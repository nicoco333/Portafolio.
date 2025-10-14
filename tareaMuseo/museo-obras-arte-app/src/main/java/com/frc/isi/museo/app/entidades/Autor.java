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

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private int Id;
    private String nombre;
    
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ObraArtistica> obras = new HashSet<>();
    public void addObra(ObraArtistica obra) {
        this.obras.add(obra);
        obra.setAutor(this);
    }
}
