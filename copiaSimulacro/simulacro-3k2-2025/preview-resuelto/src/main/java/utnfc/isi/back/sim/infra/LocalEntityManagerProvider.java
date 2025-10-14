package utnfc.isi.back.sim.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class LocalEntityManagerProvider {
    private static final String PU = "pu-backend";
    // NO static final. Debe poder recrearse.
    private static volatile EntityManagerFactory emf;

    private LocalEntityManagerProvider() {}

    private static synchronized void ensureOpen() {
        if (emf == null || !emf.isOpen()) {
            // recrea esquema "como en clase" ANTES de crear EMF
            DatabaseInitializer.recreateSchemaFromDdl();
            emf = Persistence.createEntityManagerFactory(PU);
        }
    }

    public static EntityManager em() {
        ensureOpen();
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}

