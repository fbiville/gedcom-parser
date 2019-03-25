package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.pattern.Patterns;

import java.util.function.Supplier;

import static net.biville.florent.gedcom.v55.GedcomPredicates.nonAtPredicate;
import static org.jparsec.pattern.CharPredicates.IS_ALPHA_NUMERIC_;
import static org.jparsec.pattern.CharPredicates.isChar;

public class PointerParserSupplier implements Supplier<Parser<Void>> {

    @Override
    public Parser<Void> get() {
        Parser<Void> atSymbol = Patterns.times(1, 1, isChar('@')).toScanner("@");
        Parser<Void> alphanum = Patterns.times(1, 1, IS_ALPHA_NUMERIC_).toScanner("ALPHANUM_");
        Parser<Void> nonAt = Patterns.atMost(19, nonAtPredicate()).toScanner("NOT @");
        return (alphanum.followedBy(nonAt)).between(atSymbol, atSymbol);
    }


}
