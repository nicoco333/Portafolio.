package enunciado.parcial.services;

import java.util.List;

import enunciado.parcial.entities.Puesto;
import enunciado.parcial.repositories.PuestoRepository;
import enunciado.parcial.services.interfaces.IService;

public class PuestoService implements IService<Puesto, Integer>{
    private final PuestoRepository repository;

    public PuestoService() {
        this.repository = new PuestoRepository();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public Puesto getById(Integer id) {
        return repository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     */
    @Override
    public Puesto getOrCreateByName(String nombre) {
        Puesto puesto = repository.getByName(nombre);
        if (puesto == null) {
            puesto = new Puesto();
            puesto.setNombre(nombre);
            repository.add(puesto);
        }
        return puesto;
    }

    /**
     * Devuelve todos los autores como lista.
     */
    @Override
    public List<Puesto> getAll() {
        return repository.getAllList();  // usa el genérico
    }
}