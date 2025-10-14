package com.frc.isi.museo.app.repositorios;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.frc.isi.museo.app.entidades.ObraArtistica;


public class ObraArtisticaRepository extends Repository<ObraArtistica, Integer>{
    public ObraArtisticaRepository() {
        super();
    }

    @Override
    public ObraArtistica getById(Integer id) {
        try (var stream = this.manager.createNamedQuery("ObraArtistica.GetById", ObraArtistica.class)
                .setParameter("codigo", id)
                .getResultStream()) {
            return stream
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No existe la obra con id: " + id));
        }
    }

    @Override
    public Set<ObraArtistica> getAll() {
        return this.manager.createQuery("SELECT o FROM ObraArtistica o", ObraArtistica.class)
                .getResultList()
                .stream()
                .collect(Collectors.toSet());
    }

    @Override
    public Stream<ObraArtistica> getAllStream() {
        return this.manager.createQuery("SELECT o FROM ObraArtistica o", ObraArtistica.class)
                .getResultStream();
    }

    @Override
    public boolean existeByNombreOrDescripcion(String valor){
        return this.manager.createNamedQuery("ObraArtistica.GetByNombre", ObraArtistica.class)
                    .setParameter("nombre", valor)
                    .getResultStream()
                    .findAny()
                    .isPresent();
    }
    
}
