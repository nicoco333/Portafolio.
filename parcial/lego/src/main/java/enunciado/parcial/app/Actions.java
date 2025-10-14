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

import enunciado.parcial.entities.LegoSet;
import enunciado.parcial.services.LegoSetService;
// import museo.arte.services.EstiloArtisticoService;

public class Actions {

    public void importarLegos(AppContext context) {
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
                    .filter(f -> f.getName().contains("lego")) // buscar archivo con "empleado" en el nombre
                    .findFirst() // quedarse con el primero
                    .ifPresentOrElse(f -> { // si existe:
                        // Obtener el servicio de empleados desde el contexto
                        var service = context.getService(LegoSetService.class);
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

    public void listarLegoSet(AppContext context) {
        var service = context.getService(LegoSetService.class);

        var sets = service.getAll();

        if (sets == null || sets.isEmpty()) {
            System.out.println("⚠ No hay sets LEGO registrados en la base de datos.");
            return;
        }

        System.out.println("📋 Lista de sets LEGO:");
        sets.forEach(s -> {
            String id = s.getIdSet() != null ? s.getIdSet().toString() : "—";
            String prodId = s.getProdId() != null ? s.getProdId().toString() : "—";
            String nombre = s.getSetName() != null ? s.getSetName() : "—";
            String desc = s.getProdDesc() != null ? s.getProdDesc() : "—";
            String diff = s.getReviewDifficulty() != null ? s.getReviewDifficulty() : "—";
            String piezas = s.getPieceCount() != null ? s.getPieceCount().toString() : "—";
            String rating = s.getStarRating() != null ? s.getStarRating().toPlainString() : "—";
            String precio = s.getListPrice() != null ? s.getListPrice().toPlainString() : "—";
            String theme = (s.getTheme() != null && s.getTheme().getName() != null) ? s.getTheme().getName() : "—";
            String age = (s.getAgeGroup() != null && s.getAgeGroup().getCode() != null) ? s.getAgeGroup().getCode()
                    : "—";
            String country = "—";
            if (s.getCountry() != null) {
                String cCode = s.getCountry().getCode();
                String cName = s.getCountry().getName();
                country = cName != null ? (cCode != null ? cCode + " (" + cName + ")" : cName)
                        : (cCode != null ? cCode : "—");
            }

            System.out.printf(
                    "ID:%s | PROD_ID:%s | Nombre:%s | Dificultad:%s | Piezas:%s | Rating:%s | Precio:%s | Tema:%s | Edad:%s | País:%s%n",
                    id, prodId, nombre, diff, piezas, rating, precio, theme, age, country);
            // Si querés incluir la descripción (puede ser larga):
            // System.out.printf(" Desc: %s%n", desc);
        });
    }


}
