package net.biville.florent.gedcom.v55;

import java.util.Optional;

public interface Tag {
    String getCode();
    Optional<String> getName();
    Optional<String> getDescription();
}
