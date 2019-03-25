package net.biville.florent.gedcom.v55;

import java.util.Objects;

public class Record {

    private final Line root;

    public Record(Line root) {
        this.root = root;
    }

    public Line getRoot() {
        return root;
    }

    public Tag getTag() {
        return root.getTag();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(root, record.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
