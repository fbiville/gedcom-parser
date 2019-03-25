package net.biville.florent.gedcom.v55;

import java.util.Objects;

public class LineValue {

    private final String value;

    public LineValue(String value) {
        this.value = value;
    }

    public String getValue() {
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
        final LineValue other = (LineValue) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
