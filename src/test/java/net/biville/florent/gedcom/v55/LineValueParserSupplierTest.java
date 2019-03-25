package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.error.ParserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LineValueParserSupplierTest {

    private Parser<LineValue> parser = new LineValueParserSupplier().get();

    @Test
    @DisplayName("parses line value")
    void parses_line_value() {

        String result = parser.parse("Awarded BSA Eagle Rank @@ 20EUR #cool").getValue();

        assertThat(result).isEqualTo("Awarded BSA Eagle Rank @@ 20EUR #cool");
    }


    @Test
    @DisplayName("parses line value with pointer")
    void parses_line_value_with_pointer() {

        String result = parser.parse("@some pointer@").getValue();

        assertThat(result).isEqualTo("@some pointer@");
    }


    @Test
    @DisplayName("parses line value with escape sequence")
    void parses_line_value_with_escape() {

        String result = parser.parse("@#DGREGORIAN@ 31 DEC 1997").getValue();

        assertThat(result).isEqualTo("@#DGREGORIAN@ 31 DEC 1997");
    }

    @Test
    @DisplayName("rejects invalid line value")
    void rejects_line_value() {
        assertThatThrownBy(() -> parser.parse("Only one @ will trigger a failure"))
                .isInstanceOf(ParserException.class)
                .hasMessageContaining("@ expected,   encountered");
    }
}