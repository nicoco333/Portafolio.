package com.frc.isi.museo.app.servicios;

import java.util.Map;
import java.util.HashMap;

import com.frc.isi.museo.app.entidades.Autor;
import com.frc.isi.museo.app.repositorios.AutorRepository;
import com.frc.isi.museo.app.servicios.interfaces.ILookUpOrPersistService;

public class AutorService implements ILookUpOrPersistService<Autor>{
    private final AutorRepository autorRepository;
    private final Map<String, Autor> autores;

    public AutorService() {
        autorRepository = new AutorRepository();
        autores = new HashMap<>();
    }

    @Override
    public Autor getOrCreateAutor(String nombre) {
        return this.autores.computeIfAbsent(nombre, nom -> {
            Autor autor = new Autor();
            autor.setNombre(nom);
            autorRepository.add(autor);
            return autor;
        });
    }
}
