package net.biville.florent.gedcom.v55;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class RecordTransformerTest {

    private Function<List<Line>, List<Record>> transformer = new RecordTransformer(new LineTransformer());

    @Test
    @DisplayName("creates most basic transmission records")
    void creates_basic_records() {
        Line root = headerLine();
        Line end = trailerLine();

        List<Record> records = transformer.apply(asList(root, end));

        assertThat(records).containsExactly(new Record(root), new Record(end));
    }

    @Test
    @DisplayName("creates records with nested lines")
    void creates_simple_records_with_nested_lines() {
        Line root = headerLine();
        Line child = line(new Level(1), StandardTag.CHAR, new LineValue("ASCII"));
        Line end = trailerLine();

        List<Record> records = transformer.apply(asList(root, child, end));

        assertThat(records).containsExactly(new Record(root.setChild(child)), new Record(end));
    }

    @Test
    @DisplayName("first record must be head")
    void fails_if_first_record_is_not_header() {
        Line root = line(new Level(0), StandardTag.CHAR);

        assertThatCode(() -> transformer.apply(singletonList(root)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transmission must start with HEAD");
    }

    @Test
    @DisplayName("cannot be without lines")
    void fails_if_empty() {
        assertThatCode(() -> transformer.apply(emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transmission must start with HEAD");
    }

    @Test
    @DisplayName("last record must be trlr")
    void fails_if_last_record_is_not_trailer() {
        Line root = headerLine();
        Line end = line(new Level(0), StandardTag.ABBR);

        assertThatCode(() -> transformer.apply(asList(root, end)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transmission must end with TRLR");
    }

    private Line headerLine() {
        return line(new Level(0), StandardTag.HEAD);
    }

    private Line trailerLine() {
        return line(new Level(0), StandardTag.TRLR);
    }

    private Line line(Level level, Tag tag, LineValue value) {
        return new Line(level, empty(), tag, of(value));
    }

    private Line line(Level level, Tag tag) {
        return new Line(level, empty(), tag, empty());
    }
}