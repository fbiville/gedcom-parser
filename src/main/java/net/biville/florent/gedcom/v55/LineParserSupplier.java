package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.Parsers;
import org.jparsec.Scanners;
import org.jparsec.pattern.CharPredicates;
import org.jparsec.pattern.Patterns;

import java.util.Optional;
import java.util.function.Supplier;

public class LineParserSupplier implements Supplier<Parser<Line>> {

    private final Supplier<Parser<Level>> levelParserSupplier;
    private final Supplier<Parser<CrossReferenceId>> crossReferenceIdParserSupplier;
    private final Supplier<Parser<Tag>> tagParserSupplier;
    private final Supplier<Parser<LineValue>> lineValueParserSupplier;


    public LineParserSupplier() {
        this(new LevelParserSupplier(), new CrossReferenceIdParserSupplier(), new TagParserSupplier(), new LineValueParserSupplier());
    }

    public LineParserSupplier(Supplier<Parser<Level>> levelParserSupplier,
                              Supplier<Parser<CrossReferenceId>> crossReferenceIdParserSupplier,
                              Supplier<Parser<Tag>> tagParserSupplier,
                              Supplier<Parser<LineValue>> lineValueParserSupplier) {

        this.levelParserSupplier = levelParserSupplier;
        this.crossReferenceIdParserSupplier = crossReferenceIdParserSupplier;
        this.tagParserSupplier = tagParserSupplier;
        this.lineValueParserSupplier = lineValueParserSupplier;
    }

    @Override
    public Parser<Line> get() {
        // TODO: max 255 chars
        return Parsers.sequence(
                levelParserSupplier.get().followedBy(whitespace()),
                crossReferenceIdParserSupplier.get().followedBy(whitespace()).map(Optional::ofNullable).optional(Optional.empty()),
                tagParserSupplier.get(),
                whitespace().ifelse(
                        lineValueParserSupplier.get().map(Optional::ofNullable).optional(Optional.empty()),
                        Parsers.constant(Optional.empty())
                ),
                Line::new
        ).followedBy(newline()).label("LINE");
    }

    private Parser<Void> whitespace() {
        return Patterns.times(1, 1, CharPredicates.isChar(' ')).toScanner("WHITESPACE");
    }

    private Parser<Void> newline() {
        return Scanners.string("\r\n").or(Scanners.string("\n").or(Scanners.string("\r")));
    }
}
