package net.biville.florent.gedcom.v55;

import java.util.Objects;
import java.util.Optional;

public class CustomTag implements Tag {

    private final String code;
    private final String name;
    private final String description;

    public CustomTag(String code) {
        this(code, null, null);
    }

    public CustomTag(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomTag customTag = (CustomTag) o;
        return Objects.equals(code, customTag.code) &&
                Objects.equals(name, customTag.name) &&
                Objects.equals(description, customTag.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, description);
    }

    @Override
    public String toString() {
        return code;
    }
}
