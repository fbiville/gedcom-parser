package io.github.fbiville.gedcom55;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LogicalRecord {

    private final List<GedcomLine> lines;

    public LogicalRecord(List<GedcomLine> lines) {
        this.lines = lines;
        checkLevels();
    }

    public List<GedcomLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    private void checkLevels() {
        if (this.lines.isEmpty()) {
            return;
        }

        int firstLineLevel = lines.get(0).getLevel();
        if (firstLineLevel != 0) {
            throw new IllegalStateException(String.format("First line must have level 0, found: %d", firstLineLevel));
        }

        for (int i = 0; i < lines.size() - 1; i += 2) {
            int level1 = lines.get(i).getLevel();
            int level2 = lines.get(i + 1).getLevel();
            if (level2 > level1 + 1) {
                throw new IllegalStateException(String.format(
                        "Invalid level detected: previous level was %d but subsequent level is %d", level1, level2
                ));
            }
        }
    }

    public String getRepresentation() {
        return lines.stream().map(GedcomLine::getRepresentation).reduce("", String::concat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LogicalRecord other = (LogicalRecord) obj;
        return Objects.equals(this.lines, other.lines);
    }
}
