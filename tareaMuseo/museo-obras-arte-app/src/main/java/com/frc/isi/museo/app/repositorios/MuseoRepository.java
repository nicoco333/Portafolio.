package com.frc.isi.museo.app.repositorios;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.frc.isi.museo.app.entidades.Museo;

public class MuseoRepository extends Repository<Museo, Integer>{
    public MuseoRepository() {
        super();
    }

    @Override
    public Museo getById(Integer id) {
        return this.manager.find(Museo.class, id);
    }

    @Override
    public Set<Museo> getAll() {
        return this.manager.createNamedQuery("Museo.GetAll", Museo.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Stream<Museo> getAllStream() {
        return this.manager.createQuery("SELECT m FROM Museo m", Museo.class)
                .getResultStream();
    }

    @Override
    public boolean existeByNombreOrDescripcion(String valor) {
        return this.manager.createNamedQuery("Museo.GetByNombre", Museo.class)
                .setParameter("nombre", valor)
                .getResultStream()
                .findAny()
                .isPresent();
    }
    
}
