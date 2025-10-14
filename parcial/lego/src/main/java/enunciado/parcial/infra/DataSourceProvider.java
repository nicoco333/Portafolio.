package enunciado.parcial.infra;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public class DataSourceProvider {
    public static DataSource createH2Memory() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:legos;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");
        return ds;
    }
}
