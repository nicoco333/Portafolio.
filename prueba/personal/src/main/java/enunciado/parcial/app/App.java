package enunciado.parcial.app;

import java.util.Scanner;
import java.net.URL;
import javax.sql.DataSource;

import enunciado.parcial.infra.DataSourceProvider;
import enunciado.parcial.infra.DbInitializer;

// import java.util.ArrayList;
import enunciado.parcial.menu.Menu;
import enunciado.parcial.menu.ItemMenu;

// import enunciado.parcial.services.PuestoService;
import enunciado.parcial.services.LegoSetService;

/**
 * Main application smoke test: init DB and verify JPA mapping.
 */
public class App {
    public static void main(String[] args) {
        
        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        AppContext context = AppContext.getInstance();

        // reemplaza T por AppContext como variable qeu recibe dinammicamente
        Menu<AppContext> menu = new Menu<>();
        
        // menu.setTitulo("Menu de Opciones para Museo"); // capaz agregar atributo
        URL folderPath = App.class.getResource("/files");
        context.put("path", folderPath);

        // Inicializar la BD (ejecutar el DDL provisto) antes de crear repositorios/servicios
        try {
            DataSource ds = DataSourceProvider.createH2Memory();
            DbInitializer.run(ds, "sql/ddl_legos.sql");
        } catch (Exception e) {
            System.err.println("Error inicializando la base de datos: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        context.registerService(LegoSetService.class, new LegoSetService());
        // context.registerService(EstiloArtisticoService.class, new EstiloArtisticoService());

        Actions actions = new Actions();
        menu.addOption(1, new ItemMenu<>(
            "Cargar Legos desde CSV",
            actions::importarLegos
        ));

        menu.addOption(2, new ItemMenu<>(
            "Listar todos los Legos",
            actions::listarLegoSet
        ));


        
        // inicializamos un unico scanner en appContext
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);    // preguntar sobre opciones del scanner

        menu.runMenu(context);
    }
}
