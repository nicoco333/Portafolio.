package enunciado.parcial.services;

import java.util.List;

import enunciado.parcial.entities.AgeGroup;
import enunciado.parcial.repositories.AgeGroupRepository;
import enunciado.parcial.services.interfaces.IService;

public class AgeGroupService implements IService<AgeGroup, Integer>{
    private final AgeGroupRepository repository;

    public AgeGroupService() {
        this.repository = new AgeGroupRepository();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public AgeGroup getById(Integer id) {
        return repository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     */
    @Override
    public AgeGroup getOrCreateByName(String nombre) {
        AgeGroup puesto = repository.getByName(nombre);
        if (puesto == null) {
            puesto = new AgeGroup();
            // AgeGroup usa el campo 'code' para identificar el rango de edad
            puesto.setCode(nombre);
            repository.add(puesto);
        }
        return puesto;
    }

    /**
     * Devuelve todos los autores como lista.
     */
    @Override
    public List<AgeGroup> getAll() {
        return repository.getAllList();  // usa el genérico
    }
}