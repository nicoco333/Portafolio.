package enunciado.parcial.repositories.context;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LegoDbContext {
    private final EntityManager manager;

    public static LegoDbContext instance = null;

    private LegoDbContext() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lego");
        manager = emf.createEntityManager();
    }

    public static LegoDbContext getInstance() {
        if (instance == null) {
            instance = new LegoDbContext();
        }
        return instance;
    }

    public EntityManager getManager() {
        return this.manager;
    }
}