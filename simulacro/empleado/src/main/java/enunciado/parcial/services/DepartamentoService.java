package enunciado.parcial.services;

import java.util.List;
import java.util.stream.Stream;

import enunciado.parcial.entities.Departamento;
import enunciado.parcial.repositories.DepartamentoRepository;
import enunciado.parcial.services.interfaces.IService;

public class DepartamentoService implements IService<Departamento, Integer>{
    private final DepartamentoRepository repository;

    public DepartamentoService() {
        this.repository = new DepartamentoRepository();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public Departamento getById(Integer id) {
        return repository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     */
    @Override
    public Departamento getOrCreateByName(String nombre) {
        Departamento departamento = repository.getByName(nombre);
        if (departamento == null) {
            departamento = new Departamento();
            departamento.setNombre(nombre);
            repository.add(departamento);
        }
        return departamento;
    }

    /**
     * Devuelve todos los autores como lista.
     */
    @Override
    public List<Departamento> getAll() {
        return repository.getAllList();  // usa el genérico
    }

    /**
     * Devuelve todos los autores como Stream.
     */
    public Stream<Departamento> getAllStream() {
        return repository.getAllStream();  // ya existe genérico
    }
    
}
