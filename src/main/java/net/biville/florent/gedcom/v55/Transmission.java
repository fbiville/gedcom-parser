package net.biville.florent.gedcom.v55;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Transmission {

    private final List<Record> records;

    public Transmission(List<Record> records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transmission that = (Transmission) o;
        return Objects.equals(records, that.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(records);
    }

    @Override
    public String toString() {
        return records.stream().map(Record::toString).collect(Collectors.joining("\n"));
    }
}
