package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.error.ParserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TagParserSupplierTest {

    private Parser<Tag> tagParser = new TagParserSupplier().get();

    @Test
    @DisplayName("Parses tags")
    void parses_tag() {
        String tag = tagParser.parse("AGE").getCode();

        assertThat(tag).isEqualTo("AGE");
    }

    @Test
    @DisplayName("Parses tags in lower case")
    void parses_tag_lower_case() {
        String tag = tagParser.parse("age").getCode();

        assertThat(tag).isEqualTo("AGE");
    }

    @Test
    @DisplayName("Parses custom tags")
    void parses_custom_tag() {
        String tag = tagParser.parse("_custom123").getCode();

        assertThat(tag).isEqualTo("_custom123");
    }

    @Test
    @DisplayName("Parses longest tag")
    void parses_longest_tag() {
        // there are FAM and FAMF
        String tag = tagParser.parse("FAMF").getCode();

        assertThat(tag).isEqualTo("FAMF");
    }

    @Test
    @DisplayName("Rejects custom tags without _")
    void rejects_custom_tag() {
        assertThatThrownBy(() -> tagParser.parse("custom123"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("custom tag expected, c encountered");
    }

    @Test
    @DisplayName("Rejects invalid custom tags")
    void rejects_invalid_custom_tag() {
        assertThatThrownBy(() -> tagParser.parse("_asl@1"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("EOF expected, @ encountered");
    }

    @Test
    @DisplayName("Rejects too long custom tags")
    void rejects_too_long_custom_tag() {
        assertThatThrownBy(() -> tagParser.parse("_toolooooooooooooooooooooooooong"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("EOF expected, g encountered");
    }
}