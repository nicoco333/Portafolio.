package enunciado.parcial.repositories.context;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LegoDbContext {
    private final EntityManager manager;
    private final EntityManagerFactory emf;

    public static LegoDbContext instance = null;

    private LegoDbContext() {
        this.emf = Persistence.createEntityManagerFactory("lego");
        this.manager = emf.createEntityManager();
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

    /**
     * Close the EntityManager and its factory. Safe to call multiple times.
     */
    public void shutdown() {
        try {
            if (this.manager != null && this.manager.isOpen()) this.manager.close();
        } catch (Exception e) {
            // ignore
        }
        try {
            if (this.emf != null && this.emf.isOpen()) this.emf.close();
        } catch (Exception e) {
            // ignore
        }
        instance = null;
    }
}