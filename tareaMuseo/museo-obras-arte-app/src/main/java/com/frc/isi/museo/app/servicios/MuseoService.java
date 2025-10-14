package com.frc.isi.museo.app.servicios;

import java.util.HashMap;
import java.util.Map;

import com.frc.isi.museo.app.entidades.Museo;
import com.frc.isi.museo.app.repositorios.MuseoRepository;
import com.frc.isi.museo.app.servicios.interfaces.ILookUpOrPersistService;

public class MuseoService implements ILookUpOrPersistService<Museo>{
    private final MuseoRepository museoRepository;
    private final Map<String, Museo> museos;

    public MuseoService() {
        museoRepository = new MuseoRepository();
        museos = new HashMap<>();
    }

    @Override
    public Museo getOrCreateAutor(String nombre) {
        return this.museos.computeIfAbsent(nombre, nom -> {
            Museo museo = new Museo();
            museo.setNombre(nom);
            museoRepository.add(museo);
            return museo;
        });
    }

    public boolean existe(String museo){
        return museoRepository.existeByNombreOrDescripcion(museo);
    }
}
