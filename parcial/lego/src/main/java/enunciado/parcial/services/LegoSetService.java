package enunciado.parcial.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import enunciado.parcial.entities.LegoSet;
import enunciado.parcial.entities.Theme;
import enunciado.parcial.entities.AgeGroup;
import enunciado.parcial.entities.Country;
import enunciado.parcial.repositories.LegoSetRepository;
import enunciado.parcial.services.interfaces.IService;

public class LegoSetService implements IService<LegoSet, Integer> {
    private final LegoSetRepository repository;
    private final ThemeService themeService;
    private final AgeGroupService ageGroupService;
    private final CountryService countryService;

    public LegoSetService() {
        this.repository = new LegoSetRepository();
        this.themeService = new ThemeService();
        this.ageGroupService = new AgeGroupService();
        this.countryService = new CountryService();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public LegoSet getById(Integer id) {
        return repository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     */
    @Override
    public LegoSet getOrCreateByName(String nombre) {
        LegoSet d = repository.getByName(nombre);
        if (d == null) {
            d = new LegoSet();
            d.setNombre(nombre);
            repository.add(d);
        }
        return d;
    }

    /**
     * Devuelve todos los autores como lista.
     */
    @Override
    public List<LegoSet> getAll() {
        return repository.getAllList();  // usa el genérico
    }

    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
                .skip(1) // saltear cabecera
                .forEach(linea -> {
                    LegoSet emp = this.procesarLinea(linea);
                    this.repository.add(emp);
                });
    }

    /**
     * Procesa una línea CSV y construye un LegoSet.
     * Soporta campos entrecomillados que contienen comas.
     */
    private LegoSet procesarLinea(String linea) {
        List<String> tokens = splitCsv(linea);

        // Esperamos al menos 11 columnas según la cabecera
        while (tokens.size() < 11) tokens.add("");

        LegoSet lego = new LegoSet();

        try {
            lego.setProdId(parseIntOrNull(tokens.get(0)));
        } catch (Exception ignored) {}

        lego.setSetName(tokens.get(1).trim());
        lego.setProdDesc(tokens.get(2).trim());
        lego.setReviewDifficulty(tokens.get(3).trim());
        lego.setPieceCount(parseIntOrNull(tokens.get(4)));

        try {
            String sStar = tokens.get(5).trim();
            if (!sStar.isEmpty()) lego.setStarRating(new BigDecimal(sStar));
        } catch (Exception ignored) {}

        try {
            String sPrice = tokens.get(6).trim();
            if (!sPrice.isEmpty()) lego.setListPrice(new BigDecimal(sPrice));
        } catch (Exception ignored) {}

        // Relaciones: THEME_NAME (7), AGE_GROUP_CODE (8), COUNTRY_NAME (9), COUNTRY_CODE (10)
        String themeName = tokens.get(7).trim();
        if (!themeName.isEmpty()) {
            Theme theme = themeService.getOrCreateByName(themeName);
            lego.setTheme(theme);
        }

        String ageCode = tokens.get(8).trim();
        if (!ageCode.isEmpty()) {
            AgeGroup ag = ageGroupService.getOrCreateByName(ageCode);
            lego.setAgeGroup(ag);
        }

        String countryName = tokens.get(9).trim();
        if (!countryName.isEmpty()) {
            Country c = countryService.getOrCreateByName(countryName);
            lego.setCountry(c);
        }

        return lego;
    }

    private Integer parseIntOrNull(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return null; }
    }

    /**
     * Split CSV: soporta comillas dobles que contienen comas.
     */
    private List<String> splitCsv(String line) {
        List<String> out = new ArrayList<>();
        if (line == null || line.isEmpty()) return out;
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (ch == ',' && !inQuotes) {
                out.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        out.add(cur.toString());
        return out;
    }

}