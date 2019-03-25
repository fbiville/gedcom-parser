package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.error.ParserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LevelParserSupplierTest {

    private Supplier<Parser<Level>> levelParser = new LevelParserSupplier();

    @Test
    @DisplayName("Parses line level")
    void parses_level() {
        int level = levelParser.get().parse("0").getValue();

        assertThat(level).isEqualTo(0);
    }

    @Test
    @DisplayName("Parses line level with leading space")
    void parses_level_with_leading_space() {
        int level = levelParser.get().parse("    0").getValue();

        assertThat(level).isEqualTo(0);
    }

    @Test
    @DisplayName("Fails to parse negative line level")
    void fails_parsing_negative() {
        assertThatThrownBy(() -> levelParser.get().parse("    -12"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("level 0 to 99 expected, - encountered");
    }

    @Test
    @DisplayName("Fails to parse levels above 99")
    void fails_parsing_too_big() {
        assertThatThrownBy(() -> levelParser.get().parse("    100"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("EOF expected, 0 encountered");
    }

    @Test
    @DisplayName("Fails to parse levels with leading zeroes")
    void fails_parsing_leading_zeroes() {
        assertThatThrownBy(() -> levelParser.get().parse("    07"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("EOF expected, 7 encountered");
    }
}