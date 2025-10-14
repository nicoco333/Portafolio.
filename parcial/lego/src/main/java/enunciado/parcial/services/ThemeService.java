package enunciado.parcial.services;

import java.util.List;
import java.util.stream.Stream;

import enunciado.parcial.entities.Theme;
import enunciado.parcial.repositories.ThemeRepository;
import enunciado.parcial.services.interfaces.IService;

public class ThemeService implements IService<Theme, Integer>{
    private final ThemeRepository repository;

    public ThemeService() {
        this.repository = new ThemeRepository();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public Theme getById(Integer id) {
        return repository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     */
    @Override
    public Theme getOrCreateByName(String nombre) {
        Theme theme = repository.getByName(nombre);
        if (theme == null) {
            theme = new Theme();
            theme.setName(nombre);
            repository.add(theme);
        }
        return theme;
    }

    /**
     * Devuelve todos los autores como lista.
     */
    @Override
    public List<Theme> getAll() {
        return repository.getAllList();  // usa el genérico
    }

    /**
     * Devuelve todos los autores como Stream.
     */
    public Stream<Theme> getAllStream() {
        return repository.getAllStream();  // ya existe genérico
    }
    
}
