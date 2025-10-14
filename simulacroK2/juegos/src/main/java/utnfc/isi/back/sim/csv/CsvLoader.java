package utnfc.isi.back.sim.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import com.opencsv.CSVParser;

public class CsvLoader {
    private CsvLoader(){}

    public static List<CsvGameRow> read(String path) throws Exception {
        try (var br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
        return new CsvToBeanBuilder<CsvGameRow>(br)
            .withType(CsvGameRow.class)
            .withSeparator(';')
            .withIgnoreQuotations(true)
            .withQuoteChar(CSVParser.NULL_CHARACTER)
            .withIgnoreLeadingWhiteSpace(true)
            .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
            .build()
            .parse();
        }
    }
    
}
