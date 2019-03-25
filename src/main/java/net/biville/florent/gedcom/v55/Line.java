package net.biville.florent.gedcom.v55;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

public class Line {

    private final Level level;
    private final Optional<CrossReferenceId> crossReferenceId;
    private final Tag tag;
    private final Optional<LineValue> lineValue;
    private final List<Line> children = new ArrayList<>();


    public Line(Level level, Optional<CrossReferenceId> crossReferenceId, Tag tag, Optional<LineValue> lineValue) {
        this.level = level;
        this.crossReferenceId = crossReferenceId;
        this.tag = tag;
        this.lineValue = lineValue;
    }

    public Level getLevel() {
        return level;
    }

    public int getLevelValue() {
        return level.getValue();
    }

    public Optional<CrossReferenceId> getCrossReferenceId() {
        return crossReferenceId;
    }

    public Tag getTag() {
        return tag;
    }

    public Optional<LineValue> getLineValue() {
        return lineValue;
    }

    public Line setChild(Line line) {
        children.add(line);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, crossReferenceId, tag, lineValue, children);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Line other = (Line) obj;
        return Objects.equals(this.level, other.level)
                && Objects.equals(this.crossReferenceId, other.crossReferenceId)
                && Objects.equals(this.tag, other.tag)
                && Objects.equals(this.lineValue, other.lineValue)
                && Objects.equals(this.children, other.children);
    }

    @Override
    public String toString() {
        String line = level + crossReferenceId.map(xref -> " " + xref.toString()).orElse("") + " " + tag + lineValue.map(v -> " " + v.toString()).orElse(" ");
        String children = this.children.stream().map(c -> repeat("\t", getLevelValue()) + c.toString()).collect(Collectors.joining("\n"));
        return line + (children.isEmpty() ? "" : "\n" + children);
    }

    private static String repeat(String string, int level) {
        StringBuilder builder = new StringBuilder(string.length() * level);
        for (int i = 0; i <= level; i++) {
            builder.append(string);
        }
        return builder.toString();
    }
}
