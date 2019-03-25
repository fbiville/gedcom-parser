package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LineParserSupplierIntegrationTest {

    private Parser<Line> lineParser = new LineParserSupplier().get();

    @Test
    @DisplayName("Parses simple GEDCOM file")
    void parses_simple_gedcom_file() throws IOException {
        Collection<Line> lines = readLines("/simple.ged");

        assertThat(lines).hasSize(48);
    }

    @Test
    @DisplayName("Parses complex GEDCOM file")
    void parses_complex_gedcom_file() throws IOException {
        Collection<Line> lines = readLines("/complex.ged");

        assertThat(lines).hasSize(1159);
    }


    private Collection<Line> readLines(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(LineParserSupplier.class.getResourceAsStream(filename)))) {
            return reader.lines().map(l -> String.format("%s%n", l)).map(lineParser::parse).collect(Collectors.toList());
        }
    }
}
