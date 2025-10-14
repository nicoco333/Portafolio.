package enunciado.parcial.services;

import java.util.List;
import java.util.stream.Stream;

import enunciado.parcial.entities.Country;
import enunciado.parcial.repositories.CountryRepository;
import enunciado.parcial.services.interfaces.IService;

public class CountryService implements IService<Country, Integer>{
    private final CountryRepository repository;

    public CountryService() {
        this.repository = new CountryRepository();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public Country getById(Integer id) {
        return repository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     */
    @Override
    public Country getOrCreateByName(String nombre) {
        Country country = repository.getByName(nombre);
        if (country == null) {
            country = new Country();
            country.setName(nombre);

            // Generar código base de 3 letras a partir del nombre
            String base = "UNK";
            if (nombre != null && !nombre.isBlank()) {
                base = nombre.trim().toUpperCase().replaceAll("[^A-Z]", "");
                if (base.length() >= 3) base = base.substring(0, 3);
                if (base.isBlank()) base = "UNK";
            }

            // Intentar base, si ya existe añadir sufijos numéricos hasta encontrar uno libre
            String candidate = base;
            int suffix = 0;
            final int MAX_TRIES = 1000;
            while (repository.getByCode(candidate) != null && suffix < MAX_TRIES) {
                suffix++;
                String sfx = Integer.toString(suffix);
                // Construir candidato que quepa en 3 caracteres: recortar base si es necesario
                int allowedBaseLen = Math.max(0, 3 - sfx.length());
                String truncatedBase = base.length() > allowedBaseLen ? base.substring(0, allowedBaseLen) : base;
                candidate = (truncatedBase + sfx).toUpperCase();
            }
            if (repository.getByCode(candidate) != null) {
                throw new IllegalStateException("No se pudo generar un código único de hasta 3 chars para Country: " + nombre);
            }

            country.setCode(candidate);
            repository.add(country);
        }
        return country;
    }

    /**
     * Devuelve todos los autores como lista.
     */
    @Override
    public List<Country> getAll() {
        return repository.getAllList();  // usa el genérico
    }

    /**
     * Devuelve todos los autores como Stream.
     */
    public Stream<Country> getAllStream() {
        return repository.getAllStream();  // ya existe genérico
    }
    
}
