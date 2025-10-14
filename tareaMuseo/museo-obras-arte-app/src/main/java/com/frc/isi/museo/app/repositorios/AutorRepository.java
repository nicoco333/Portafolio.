package com.frc.isi.museo.app.repositorios;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.frc.isi.museo.app.entidades.Autor;

public class AutorRepository extends Repository<Autor, Integer> {
    public AutorRepository(){
        super();
    }
    
    @Override
    public Autor getById(Integer id) {
        return this.manager.find(Autor.class, id);
    }

    @Override
    public Set<Autor> getAll() {
        return this.manager.createQuery("SELECT a FROM Autor a", Autor.class)
                .getResultList()
                .stream().collect(Collectors.toSet());
    }

    @Override
    public Stream<Autor> getAllStream() {
        return this.manager.createQuery("SELECT o FROM Autor a", Autor.class)
                .getResultStream();
    }
}
