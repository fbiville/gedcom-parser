package net.biville.florent.gedcom.v55;

import java.util.Objects;

public class Level {

    private final int value;

    public Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Level other = (Level) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }
}
