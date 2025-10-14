package enunciado.parcial.repositories;

import enunciado.parcial.entities.Country;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CountryRepository extends Repository <Country, Integer>{
    public CountryRepository() {
        super();
    }

    @Override
    protected Class<Country> getEntityClass(){
        return Country.class;
    }

    /**
     * Busca un Country por su código.
     */
    public Country getByCode(String code) {
        TypedQuery<Country> q = manager.createQuery("SELECT c FROM Country c WHERE c.code = :code", Country.class);
        q.setParameter("code", code);
        List<Country> r = q.getResultList();
        return r.isEmpty() ? null : r.get(0);
    }
}