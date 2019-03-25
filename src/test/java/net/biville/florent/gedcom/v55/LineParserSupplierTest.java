package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LineParserSupplierTest {

    private Parser<Line> parser = new LineParserSupplier().get();

    @Test
    @DisplayName("parses basic line")
    void parses_line() {
        Line line = parser.parse("0 HEAD\n");

        assertThat(line.getLevelValue()).isEqualTo(0);
        assertThat(line.getCrossReferenceId()).isEmpty();
        assertThat(line.getTag().getCode()).isEqualTo("HEAD");
        assertThat(line.getLineValue()).isEmpty();
    }

    @Test
    @DisplayName("parses line with cross-reference ID")
    void parses_line_with_xref() {
        Line line = parser.parse("0 @PERSON1@ INDI\r");

        assertThat(line.getLevelValue()).isEqualTo(0);
        assertThat(line.getCrossReferenceId()).hasValue(new CrossReferenceId("@PERSON1@"));
        assertThat(line.getTag().getCode()).isEqualTo("INDI");
        assertThat(line.getLineValue()).isEmpty();
    }

    @Test
    @DisplayName("parses line with value")
    void parses_line_with_value() {
        Line line = parser.parse("2 PLAC Bourgeauville,14430,Calvados,Basse-Normandie,FRANCE,\n");

        assertThat(line.getLevelValue()).isEqualTo(2);
        assertThat(line.getCrossReferenceId()).isEmpty();
        assertThat(line.getTag().getCode()).isEqualTo("PLAC");
        assertThat(line.getLineValue()).hasValue(new LineValue("Bourgeauville,14430,Calvados,Basse-Normandie,FRANCE,"));
    }

    @Test
    @DisplayName("parses line with escape value")
    void parses_line_with_escape_value() {
        Line line = parser.parse("2 DATE @#DGREGORIAN@ 31 DEC 1997\r\n");

        assertThat(line.getLevelValue()).isEqualTo(2);
        assertThat(line.getCrossReferenceId()).isEmpty();
        assertThat(line.getTag().getCode()).isEqualTo("DATE");
        assertThat(line.getLineValue()).hasValue(new LineValue("@#DGREGORIAN@ 31 DEC 1997"));
    }

    @Test
    @DisplayName("parses line with special chars")
    void parses_line_with_special_chars() {
        Line line = parser.parse("3 PHON Corporation phone number 3 (last one!)\n");

        assertThat(line.getLevelValue()).isEqualTo(3);
        assertThat(line.getCrossReferenceId()).isEmpty();
        assertThat(line.getTag().getCode()).isEqualTo("PHON");
        assertThat(line.getLineValue()).hasValue(new LineValue("Corporation phone number 3 (last one!)"));
    }

    @Test
    @DisplayName("parses line with trailing space")
    void parses_line_with_trailing_space() {
        Line line = parser.parse("1 CHAN \r\n");

        assertThat(line.getLevelValue()).isEqualTo(1);
        assertThat(line.getCrossReferenceId()).isEmpty();
        assertThat(line.getTag().getCode()).isEqualTo("CHAN");
        assertThat(line.getLineValue()).isEmpty();
    }

    @Test
    @Disabled("FIXME: ambiguity with space as an authorized trailing value delimiter or as part of the value")
    @DisplayName("parses line with trailing space after value")
    void parses_line_with_trailing_space_after_value() {
        Line line = parser.parse("1 CHAN some value \r\n");

        assertThat(line.getLevelValue()).isEqualTo(1);
        assertThat(line.getCrossReferenceId()).isEmpty();
        assertThat(line.getTag().getCode()).isEqualTo("CHAN");
        assertThat(line.getLineValue()).hasValue(new LineValue("some value"));
    }
}