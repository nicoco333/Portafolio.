package enunciado.parcial.infra;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

public class DbInitializer {
    public static void run(DataSource ds, String ddlResourcePath) throws Exception {
        try (Connection conn = ds.getConnection()) {
            InputStream is = DbInitializer.class.getClassLoader().getResourceAsStream(ddlResourcePath);
            if (is == null) {
                throw new IllegalStateException("DDL resource not found: " + ddlResourcePath);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                String[] statements = sb.toString().split(";\n");
                for (String stmt : statements) {
                    String s = stmt.trim();
                    if (!s.isEmpty()) {
                        try (Statement st = conn.createStatement()) {
                            st.execute(s);
                        }
                    }
                }
            }
        }
    }
}
