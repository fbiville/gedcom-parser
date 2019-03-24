package io.github.fbiville.gedcom55;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LogicalRecordTest {

    @Test
    @DisplayName("CONC discards the carriage return")
    void concatenation_with_conc() {
        LogicalRecord logicalRecord = new LogicalRecord(Arrays.asList(new GedcomLine(0, "@someID@", "hello"), new GedcomLine(1, "@someID@", "CONC world")));

        assertThat(logicalRecord.getRepresentation()).isEqualTo("hello world");
    }

    @Test
    @DisplayName("CONT keeps the carriage return")
    void concatenation_with_cont() {
        LogicalRecord logicalRecord = new LogicalRecord(Arrays.asList(new GedcomLine(0, "@someID@", "hello"), new GedcomLine(1, "@someID@", "CONT world")));

        assertThat(logicalRecord.getRepresentation()).isEqualTo("hello\n world");
    }

    @Test
    @DisplayName("first line must have level 0")
    void first_line_level() {
        assertThatThrownBy(() -> new LogicalRecord(singletonList(new GedcomLine(2, "@someID@", "hello"))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("First line must have level 0, found: 2");
    }

    @Test
    @DisplayName("an empty record is valid")
    void no_lines() {
        assertThatCode(() -> new LogicalRecord(Collections.emptyList()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("a line level cannot be higher by more than 1 than the previous line")
    void line_levels_cannot_grow_too_much() {
        assertThatThrownBy(() -> new LogicalRecord(Arrays.asList(new GedcomLine(0, "@someID@", "hello"), new GedcomLine(3, "@someID@", "world"))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid level detected: previous level was 0 but subsequent level is 3");
    }

}