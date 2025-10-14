package enunciado.parcial.app;

import java.io.File;
import java.net.URL;
import javax.sql.DataSource;

import enunciado.parcial.infra.DataSourceProvider;
import enunciado.parcial.infra.DbInitializer;
import enunciado.parcial.repositories.context.LegoDbContext;
import enunciado.parcial.services.LegoSetService;

public class AutoImportRunner {
    public static void main(String[] args) throws Exception {
        AppContext context = AppContext.getInstance();

        URL folderPath = AutoImportRunner.class.getResource("/files");
        context.put("path", folderPath);

        // Inicializar la BD
        DataSource ds = DataSourceProvider.createH2Memory();
        DbInitializer.run(ds, "sql/ddl_legos.sql");

        LegoSetService service = new LegoSetService();

        // Buscar el CSV en resources/files
        URL csvUrl = AutoImportRunner.class.getResource("/files/lego.csv");
        if (csvUrl == null) {
            System.err.println("No se encontró /files/lego.csv en resources. Coloca el CSV en src/main/resources/files/");
            // cerrar context
            LegoDbContext.getInstance().shutdown();
            System.exit(1);
        }

        File csv = new File(csvUrl.toURI());
        System.out.println("Importando: " + csv.getAbsolutePath());
        service.bulkInsert(csv);

        System.out.println("Import completo. Lista de sets:");
        service.getAll().forEach(s -> System.out.println(s));

        // Shutdown JPA context cleanly
        LegoDbContext.getInstance().shutdown();
    }
}
