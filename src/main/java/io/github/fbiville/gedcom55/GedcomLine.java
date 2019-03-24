package io.github.fbiville.gedcom55;

import java.util.Objects;

public class GedcomLine {

    private final int level;
    private final String crossReferenceId;
    private final String contents;

    public GedcomLine(int level, String crossReferenceId, String contents) {
        this.level = level;
        this.crossReferenceId = crossReferenceId;
        this.contents = contents;
        checkLevel();
        checkCrossReferenceId();
    }

    public String getRepresentation() {
        return contents.replace("CONC", "").replace("CONT", "\n");
    }

    public int getLevel() {
        return level;
    }

    public String getCrossReferenceId() {
        return crossReferenceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, crossReferenceId, contents);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final GedcomLine other = (GedcomLine) obj;
        return Objects.equals(this.level, other.level)
                && Objects.equals(this.crossReferenceId, other.crossReferenceId)
                && Objects.equals(this.contents, other.contents);
    }

    private void checkLevel() {
        int level = this.level;
        if (level > 99) {
            throw new IllegalStateException(
                    String.format("Invalid level detected: level cannot be higher than 99, found: %d", level)
            );
        }
    }

    private void checkCrossReferenceId() {
        int characterCount = this.crossReferenceId.codePointCount(0, this.crossReferenceId.length());
        if (characterCount > 22) {
            throw new IllegalStateException(
                    String.format("Invalid cross-reference ID: its length cannot exceed 22 characters, found: %d", characterCount)
            );
        }
    }
}
