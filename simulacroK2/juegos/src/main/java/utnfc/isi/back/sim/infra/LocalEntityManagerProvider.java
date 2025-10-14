package utnfc.isi.back.sim.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class LocalEntityManagerProvider {
    private static final String PU = "pu-backend";
    private static EntityManagerFactory EMF;

    static{
        // Initialize lazily via recreateIfNeeded()
        recreateIfNeeded();
    }

    private static synchronized void recreateIfNeeded() {
        if (EMF == null || !EMF.isOpen()) {
            // Recreate schema and (re)build the EMF
            DatabaseInitializer.recreateSchemaFromDdl();
            EMF = Persistence.createEntityManagerFactory(PU);
        }
    }

    private LocalEntityManagerProvider(){}

    public static EntityManager em(){
        if (EMF == null || !EMF.isOpen()) recreateIfNeeded();
        return EMF.createEntityManager();
    }

    public static void close(){
        if (EMF != null && EMF.isOpen()) EMF.close();
    }
}
