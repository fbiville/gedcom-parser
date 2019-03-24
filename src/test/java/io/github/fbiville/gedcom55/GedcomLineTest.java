package io.github.fbiville.gedcom55;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GedcomLineTest {

    @Test
    @DisplayName("CONC discards the carriage return")
    void display_with_conc() {
        GedcomLine line = new GedcomLine(0, "@someID@", "CONC something");

        assertThat(line.getRepresentation()).isEqualTo(" something");
    }

    @Test
    @DisplayName("CONC keeps the carriage return")
    void display_with_cont() {
        GedcomLine line = new GedcomLine(0, "@someID@", "CONT something");

        assertThat(line.getRepresentation()).isEqualTo("\n something");
    }

    @Test
    @DisplayName("line level cannot be higher than 99")
    void max_level() {
        assertThatThrownBy(() -> new GedcomLine(100, "@someID@", "something"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid level detected: level cannot be higher than 99, found: 100");
    }

    @Test
    @DisplayName("rejects cross reference IDs longer than 22 characters")
    void cross_reference_id_character_count() {
        assertThatThrownBy(() -> new GedcomLine(0, "@somewaytoolongIDoops@😅", "something"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid cross-reference ID: its length cannot exceed 22 characters, found: 23");
    }
}