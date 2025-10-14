package enunciado.parcial.repositories;

import java.util.Set;
import java.util.List;
import java.util.stream.Stream;
import java.lang.reflect.Field;
import jakarta.persistence.TypedQuery; // Consulta tipada en JPA/Hibernate
import java.util.stream.Collectors;

import enunciado.parcial.repositories.context.LegoDbContext;
import jakarta.persistence.EntityManager;

public abstract class Repository<T, K> {
    protected EntityManager manager;

    public Repository() {
        manager = LegoDbContext.getInstance().getManager();
    }

    public void add(T entity) {
        var transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(entity);
        transaction.commit();
    }

    public void update(T entity) {
        var transaction = manager.getTransaction();
        transaction.begin();
        manager.merge(entity);
        transaction.commit();
    }
    
    public T delete(K id) {
        var transaction = manager.getTransaction();
        transaction.begin();
        var entity = this.getById(id);
        manager.remove(entity);
        transaction.commit();
        return entity;
    }

   // =========================
    // MÉTODOS ABSTRACTOS
    // =========================

    /* Read genericos */
    public Set<T> getAllSet() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultList().stream().collect(Collectors.toSet());
    }

    public List<T> getAllList() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultList();
    }

    public Stream<T> getAllStream() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultStream();
    }

    /**
     * Recupera una entidad por su id (PRIMARY KEY).
     * - Abstracto porque depende del tipo de entidad (Museo, Obra, etc.)
     * - Se implementa con manager.find(Entidad.class, id)
     */
    public T getById(K id) {
        // Esto es genérico: usa manager.find() con la clase de la entidad
        return manager.find(getEntityClass(), id);
    }

    /**
     * Método genérico para recuperar una entidad por su campo 'nombre'.
     * - Funciona si la entidad T tiene un atributo llamado 'nombre'.
     * - Devuelve null si no se encuentra ninguna coincidencia.
     */
    public T getByName(String name) {
        // Detectamos si la entidad tiene el atributo 'name' o 'nombre' y construimos la JPQL en consecuencia.
    Field[] fields = getEntityClass().getDeclaredFields();
    // Atributos comunes que pueden representar el 'nombre' de la entidad.
    String[] candidates = new String[]{"name", "nombre", "code", "codigo"};

    String attribute = Stream.of(fields)
        .map(Field::getName)
        .filter(n -> Stream.of(candidates).anyMatch(c -> c.equalsIgnoreCase(n)))
        .findFirst()
        .orElse(null);

    if (attribute == null) {
        throw new IllegalStateException("No se encontró atributo entre " + java.util.Arrays.toString(candidates) + " en la entidad " + getEntityClass().getSimpleName());
    }

        String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e." + attribute + " = :name";
        TypedQuery<T> query = manager.createQuery(jpql, getEntityClass());
        query.setParameter("name", name);
        List<T> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Método abstracto que debe implementar cada subclase
     * para devolver la clase concreta de la entidad T.
     * Esto es necesario para construir queries genéricas.
     */
    protected abstract Class<T> getEntityClass();

}