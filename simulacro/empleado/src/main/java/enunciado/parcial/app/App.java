package enunciado.parcial.app;

import java.util.Scanner;
import java.net.URL;

// import java.util.ArrayList;
import enunciado.parcial.menu.Menu;
import enunciado.parcial.menu.ItemMenu;

// import enunciado.parcial.services.PuestoService;
import enunciado.parcial.services.EmpleadoService;

public class App {
    public static void main(String[] args) {
        
        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        AppContext context = AppContext.getInstance();

        // reemplaza T por AppContext como variable qeu recibe dinammicamente
        Menu<AppContext> menu = new Menu<>();
        
        // menu.setTitulo("Menu de Opciones para Museo"); // capaz agregar atributo
        URL folderPath = App.class.getResource("/files");
        context.put("path", folderPath);
        context.registerService(EmpleadoService.class, new EmpleadoService());
        // context.registerService(EstiloArtisticoService.class, new EstiloArtisticoService());

        Actions actions = new Actions();
        menu.addOption(1, new ItemMenu<>(
            "Cargar empleados desde CSV", 
            actions::importarEmpleados
        ));

        menu.addOption(2, new ItemMenu<>(
            "Mostrar la cantidad de empleados fijos y no fijos", 
            actions::contarEmpleadosFijoYNoFijos
        ));

        menu.addOption(3, new ItemMenu<>(
            "Listar cada empleado con su salario final calculado.", 
            actions::mostrarSalarioFinal
        ));

        menu.addOption(4, new ItemMenu<>(
            "Mostrar cantidad de empleados por departamento", 
            actions::mostrarCantidadEmpleadosDepartamento
        ));


        menu.addOption(5, new ItemMenu<>(
            "Listar el salario promedio por puesto", 
            actions::mostrarSalarioPromedioPuesto
        ));

        menu.addOption(6, new ItemMenu<>(
            "Generar reporte de empleados por departamento", 
            actions::generarArchivoEmpleadosPorDepartamento
        ));
        
         menu.addOption(7, new ItemMenu<>(
            "Buscar un empleado por su id", 
            actions::buscarEmpleadoPorId
        ));


        
        // inicializamos un unico scanner en appContext
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);    // preguntar sobre opciones del scanner

        menu.runMenu(context);
    }
}

