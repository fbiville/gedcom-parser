package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.error.ParserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CrossReferenceIdParserSupplierTest {

    private Parser<CrossReferenceId> parser = new CrossReferenceIdParserSupplier().get();

    @Test
    @DisplayName("Parses cross reference IDs")
    void parses_cross_ref() {
        CrossReferenceId crossReferenceId = parser.parse("@some valid id@");

        assertThat(crossReferenceId.getValue()).isEqualTo("@some valid id@");
    }

    @Test
    @DisplayName("Rejects cross reference IDs not starting with alphanumerical/_ characters")
    void rejects_wrong_cross_ref_start() {
        assertThatThrownBy(() -> parser.parse("@ invalid id@"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("ALPHANUM_ expected");
    }

    @Test
    @DisplayName("Rejects cross reference IDs with reserved characters")
    void rejects_wrong_cross_ref_with_reserved_characters() {
        assertThatThrownBy(() -> parser.parse("@invalid:id@"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("@ expected");
    }

    @Test
    @DisplayName("Rejects too long cross reference IDs")
    void rejects_too_long_cross_ref() {
        assertThatThrownBy(() -> parser.parse("@toolooooooooooooooong@"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("@ expected");
    }
}