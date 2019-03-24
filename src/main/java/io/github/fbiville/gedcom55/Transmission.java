package io.github.fbiville.gedcom55;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;

public class Transmission {

    private final List<LogicalRecord> records;

    public Transmission(List<LogicalRecord> records) {
        this.records = records;
        checkCrossReferenceIds();
    }

    private void checkCrossReferenceIds() {
        Map<String, Long> referenceCount = this.records.stream()
                .flatMap(r -> r.getLines().stream())
                .map(GedcomLine::getCrossReferenceId)
                .collect(Collectors.groupingBy(identity(), counting()));

        String errors = referenceCount.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(e -> String.format("\"%s\" duplicated %d time(s)", e.getKey(), e.getValue() - 1))
                .collect(Collectors.joining(", "));

        if (!errors.isEmpty()) {
            throw new IllegalStateException(String.format("Invalid cross-reference IDs detected: %s", errors));
        }
    }
}
