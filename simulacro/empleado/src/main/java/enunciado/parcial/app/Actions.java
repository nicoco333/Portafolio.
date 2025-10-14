package enunciado.parcial.app;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Map;

import enunciado.parcial.entities.Empleado;
import enunciado.parcial.services.EmpleadoService;
// import museo.arte.services.EstiloArtisticoService;

public class Actions {

    public void importarEmpleados(AppContext context) {
        // Obtiene del contexto (AppContext) la URL donde están los archivos a importar
        var pathToImport = (URL) context.get("path");

        // Bloque try-with-resources: recorre todos los archivos dentro del directorio
        // indicado
        try (var paths = Files.walk(Paths.get(pathToImport.toURI()))) {

            // Se filtran los archivos encontrados:
            // 1. Solo se toman archivos regulares (no directorios)
            // 2. Que terminen en ".csv"
            // 3. Luego se convierten a objetos File y se guardan en una lista
            var csvFiles = paths
                    .filter(Files::isRegularFile) // solo archivos, no carpetas
                    .filter(path -> path.toString().endsWith(".csv")) // que terminen en ".csv"
                    .map(path -> path.toFile()) // convertir Path → File
                    .toList(); // recolectar en lista

            // Se procesa la lista de archivos CSV:
            // 1. Busca el primer archivo cuyo nombre contenga la palabra "empleado"
            // 2. Si lo encuentra → lo pasa al servicio para cargar empleados
            // 3. Si no lo encuentra → lanza una excepción
            csvFiles.stream()
                    .filter(f -> f.getName().contains("empleados")) // buscar archivo con "empleado" en el nombre
                    .findFirst() // quedarse con el primero
                    .ifPresentOrElse(f -> { // si existe:
                        // Obtener el servicio de empleados desde el contexto
                        var service = context.getService(EmpleadoService.class);
                        try {
                            // Insertar en bloque todos los empleados del archivo CSV
                            service.bulkInsert(f);
                        } catch (IOException e) {
                            e.printStackTrace(); // manejar error de lectura del archivo
                        }
                    },
                            () -> {
                                // Si no se encontró ningún archivo válido, lanzar excepción
                                throw new IllegalArgumentException("Archivo inexistente");
                            });

        } catch (IOException | URISyntaxException e) {
            // Manejo de errores: problemas de acceso al archivo o conversión de URI
            e.printStackTrace();
        }
    }

    public void listarEmpleados(AppContext context) {
        var service = context.getService(EmpleadoService.class);

        // Recuperar todas las obras desde la BD
        var empleados = service.getAll();

        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registradas en la base de datos.");
        } else {
            System.out.println("📋 Lista de empleados:");
            empleados.forEach(emp -> {
                System.out.printf(
                        "ID: %d | Nombre: %s | Año: %s | Puesto: %s | Departamento: %s | Salario: %.2f | Empleado Fijo: %s%n",
                        emp.getId(),
                        emp.getNombre(),
                        emp.getFechaIngreso().toString(),
                        emp.getPuesto() != null ? emp.getPuesto().getNombre() : "Desconocido",
                        emp.getDepartamento() != null ? emp.getDepartamento().getNombre() : "Desconocido",
                        emp.getSalario(),
                        emp.isEmpleadoFijo() ? "Sí" : "No");
            });
        }
    }

    // opcion 2
    public void contarEmpleadosFijoYNoFijos(AppContext context) {
        // 1️⃣ Obtener el servicio de empleados desde el contexto de la aplicación
        var service = context.getService(EmpleadoService.class);

        // 2️⃣ Recuperar la lista completa de empleados desde la base de datos
        var empleados = service.getAll();

        // 3️⃣ Mandar la lista de empleados al Empleado Service para que aplique la
        // logica de negocio
        Map<Boolean, Long> resultado = service.contarFijosYNoFijos(empleados);

        // 4️⃣ Mostrar el resultado
        System.out.println("Empleados fijos: " + resultado.get(true));
        System.out.println("Empleados no fijos: " + resultado.get(false));
    }

    // opcion 3 Listar cada empleado con su salario final calculado.
    public void mostrarSalarioFinal(AppContext context) {
        var service = context.getService(EmpleadoService.class);

        // Recuperar todas las obras desde la BD
        var empleados = service.getAll();
        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registradas en la base de datos.");
        } else {
            System.out.println("📋 Lista de empleados:");
            empleados.forEach(emp -> {
                System.out.printf(
                        "Nombre: %s |  Salario: %.2f | Salario Final: %2f %n",
                        emp.getNombre(),
                        emp.getSalario(),
                        emp.calcularSalarioFinal());
            });
        }
    }

    // opcion 4 Mostrar cantidad de empleados por departamento

    public void mostrarCantidadEmpleadosDepartamento(AppContext context) {
        var service = context.getService(EmpleadoService.class);

        // Recuperar todas las obras desde la BD
        var empleados = service.getAll();
        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registradas en la base de datos.");
        } else {
            System.out.println("📋 Cantidad de empleados por departamento:");
            var resultado = empleados.stream()
                    .collect(
                            Collectors.groupingBy( // Agrupa los elementos por departamento
                                    e -> e.getDepartamento().getNombre(), // Función que devuelve el nombre del
                                                                          // departamento
                                    Collectors.counting() // Contar cuántos empleados hay en cada departamento
                            ));
            resultado.forEach((depa, count) -> {
                System.out.printf("Departamento: %s | Cantidad de Empleados: %d%n", depa, count);
            });
        }
    }

    // opcion 5 Listar el salario promedio por puesto
    public void mostrarSalarioPromedioPuesto(AppContext context) {
        var service = context.getService(EmpleadoService.class);

        // Recuperar todas las obras desde la BD
        var empleados = service.getAll();
        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registradas en la base de datos.");
        } else {
            System.out.println("📋 Salario promedio por puesto:");
            var resultado = empleados.stream()
                    .collect(
                            Collectors.groupingBy( // Agrupa los elementos por puesto
                                    e -> e.getPuesto().getNombre(), // Función que devuelve el nombre del puesto
                                    Collectors.averagingDouble(e -> e.getSalario()) // Calcular el salario promedio en
                                                                                    // cada puesto
                            ));
            resultado.forEach((puesto, avgSalary) -> {
                System.out.printf("Puesto: %s | Salario Promedio: %.2f%n", puesto, avgSalary);
            });
        }
    }

    // opcion 6 Generar un archivo de texto que liste cada departamento y la
    // cantidad de empleados que pertenecen a él.
    public void generarArchivoEmpleadosPorDepartamento(AppContext context) {
        var service = context.getService(EmpleadoService.class);

        var empleados = service.getAll();
        if (empleados == null || empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registrados en la base de datos.");
            return;
        }

        // Agrupa por nombre de departamento (con manejo de null/blank)
        Map<String, Long> mapaDepartamentos = empleados.stream()
                .collect(Collectors.groupingBy(
                        e -> {
                            var d = e.getDepartamento();
                            var nombre = (d == null) ? null : d.getNombre();
                            return (nombre == null || nombre.isBlank()) ? "(Sin departamento)" : nombre.trim();
                        },
                        Collectors.counting()));

        // Mostrar por consola (orden alfabético por nombre de departamento)
        System.out.println("📋 Cantidad de empleados por departamento:");
        mapaDepartamentos.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf("Departamento: %s | Cantidad de Empleados: %d%n",
                        entry.getKey(), entry.getValue()));

        // Crear carpeta y escribir CSV
        Path dir = Paths.get("C:\\facultad\\2025\\backend\\simulacro\\empleado\\src\\main\\resources");
        Path path = dir.resolve("reporte_departamentos.csv");

        try {
            Files.createDirectories(dir);
            try (BufferedWriter writer = Files.newBufferedWriter(
                    path, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {

                writer.write("Departamento,Cantidad de Empleados");
                writer.newLine();

                mapaDepartamentos.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> {
                            try {
                                // Por si algún nombre tiene coma, la reemplazo para no romper el CSV
                                String dep = entry.getKey().replace(',', ' ');
                                writer.write(dep + "," + entry.getValue());
                                writer.newLine();
                            } catch (IOException ex) {
                                throw new UncheckedIOException(ex);
                            }
                        });
            }

            System.out.println("\n✅ Archivo generado correctamente: " + path.toAbsolutePath());
        } catch (IOException | UncheckedIOException ex) {
            System.err.println("❌ Error al escribir el archivo: " + ex.getMessage());
        }
    }

    // opcion 7 Buscar un empleado por su id
    public void buscarEmpleadoPorId(AppContext context) {
        var service = context.getService(EmpleadoService.class);

        // solicitar y validar id desde el Scanner del AppContext
        Scanner scanner = context.get("scanner", Scanner.class);
        System.out.print("Ingrese el id del empleado: ");

        Integer id = null;
        while (id == null) {
            if (!scanner.hasNextInt()) {
                String bad = scanner.next();
                System.out.print("Entrada inválida ('" + bad + "'). Ingrese un número entero: ");
                continue;
            }
            id = scanner.nextInt();
        }

        var empleado = service.getById(id);
        if (empleado == null) {
            System.out.println("No se encontró empleado con id " + id);
        } else {
            System.out.printf("Empleado: ID=%d | Nombre=%s | Departamento=%s | Puesto=%s%n",
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getDepartamento() != null ? empleado.getDepartamento().getNombre() : "Desconocido",
                    empleado.getPuesto() != null ? empleado.getPuesto().getNombre() : "Desconocido");
        }
    }
}
