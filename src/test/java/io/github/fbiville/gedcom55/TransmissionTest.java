package io.github.fbiville.gedcom55;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransmissionTest {

    @Test
    @DisplayName("a cross-reference ID must be unique within a transmission")
    void cross_reference_id_globally_unique() {
        assertThatThrownBy(() -> new Transmission(asList(
                new LogicalRecord(singletonList(new GedcomLine(0, "@sameID@", "hello"))),
                new LogicalRecord(singletonList(new GedcomLine(0, "@sameID@", "hi")))))
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid cross-reference IDs detected: \"@sameID@\" duplicated 1 time(s)");
    }

    @Test
    @DisplayName("cross-reference IDs must be unique within a transmission")
    void cross_reference_id_globally_unique_bis() {
        assertThatThrownBy(() -> new Transmission(asList(
                new LogicalRecord(asList(new GedcomLine(0, "@sameID@", "hello"), new GedcomLine(0, "@sameID2@", "hi"))),
                new LogicalRecord(singletonList(new GedcomLine(0, "@sameID@", "naber"))),
                new LogicalRecord(singletonList(new GedcomLine(0, "@sameID2@", "salut"))),
                new LogicalRecord(singletonList(new GedcomLine(0, "@sameID2@", "hola")))))
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid cross-reference IDs detected: \"@sameID@\" duplicated 1 time(s), \"@sameID2@\" duplicated 2 time(s)");
    }

}