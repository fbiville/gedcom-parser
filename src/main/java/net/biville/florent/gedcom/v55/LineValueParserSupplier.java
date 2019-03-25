package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.Scanners;
import org.jparsec.pattern.CharPredicates;
import org.jparsec.pattern.Patterns;

import java.util.function.Supplier;

import static net.biville.florent.gedcom.v55.GedcomPredicates.nonAtPredicate;
import static net.biville.florent.gedcom.v55.GedcomPredicates.otherCharPredicate;

public class LineValueParserSupplier implements Supplier<Parser<LineValue>> {

    private final Supplier<Parser<Void>> pointerParserSupplier;

    LineValueParserSupplier() {
        this(new PointerParserSupplier());
    }

    public LineValueParserSupplier(Supplier<Parser<Void>> pointerParserSupplier) {
        this.pointerParserSupplier = pointerParserSupplier;
    }

    @Override
    public Parser<LineValue> get() {
        return pointerParserSupplier.get().source()
                .or(anyCharParser())
                .or(escapeParser())
                .map(LineValue::new)
                .label("LINE VALUE");
    }

    private Parser<String> escapeParser() {
        return Scanners.isChar('@').next(Scanners.isChar('#'))
                .next(anyCharParser())
                .next(Scanners.isChar('@'))
                .next(Patterns.atLeast(1, nonAtPredicate()).toScanner("non at"))
                .source();
    }

    private Parser<String> anyCharParser() {
        return Scanners.isChar('@').next(Scanners.isChar('@'))
                .or(Patterns.many1(
                        CharPredicates.or(
                                CharPredicates.IS_ALPHA_NUMERIC_,
                                otherCharPredicate(),
                                CharPredicates.isChar('#'),
                                CharPredicates.isChar(' '))).toScanner("any not @@"))
                .atLeast(1).source();
    }
}


