package enunciado.parcial.infra;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.sql.DataSource;

public class LocalEntityManagerProvider {
    private static EntityManagerFactory emf;

    public static synchronized EntityManagerFactory getEntityManagerFactory(DataSource ds) {
        if (emf == null) {
            Map<String, Object> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:legos;DB_CLOSE_DELAY=-1");
            props.put("jakarta.persistence.jdbc.user", "sa");
            props.put("jakarta.persistence.jdbc.password", "");
            props.put("jakarta.persistence.jdbc.driver", "org.h2.Driver");
            props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            props.put("hibernate.hbm2ddl.auto", "none");
            emf = Persistence.createEntityManagerFactory("legosPU", props);
        }
        return emf;
    }
}
