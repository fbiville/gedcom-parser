package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransmissionParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransmissionParser.class);

    private final Parser<Line> lineParser;
    private final Function<List<Line>, List<Record>> recordTransformer;

    public TransmissionParser(Parser<Line> lineParser, Function<List<Line>, List<Record>> recordTransformer) {
        this.lineParser = lineParser;
        this.recordTransformer = recordTransformer;
    }

    // TODO: build index for record pointers
    public Transmission parse(Reader gedcomFile) {
        try (BufferedReader bufferedReader = new BufferedReader(gedcomFile)) {
            return new Transmission(recordTransformer.apply(parseLines(bufferedReader)));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private List<Line> parseLines(BufferedReader bufferedReader) {
        return bufferedReader.lines()
                .map(line -> {
                    LOGGER.debug(line);
                    return lineParser.parse(line + "\n");
                })
                .collect(Collectors.toList());
    }
}
