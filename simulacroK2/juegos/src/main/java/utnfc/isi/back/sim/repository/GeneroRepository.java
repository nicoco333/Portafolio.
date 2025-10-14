package utnfc.isi.back.sim.repository;

import java.util.Optional;
import java.util.List;

import utnfc.isi.back.sim.domain.Genero;

public interface GeneroRepository extends CrudRepository <Genero, Integer> {
    public List<Genero> findAllOrderByNombre();
    
    public Optional<Genero> findByNombre(String nombre);
}
