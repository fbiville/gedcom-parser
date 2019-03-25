package net.biville.florent.gedcom.v55;

import org.jparsec.pattern.CharPredicate;
import org.jparsec.pattern.CharPredicates;

import static org.jparsec.pattern.CharPredicates.IS_ALPHA_NUMERIC_;
import static org.jparsec.pattern.CharPredicates.and;
import static org.jparsec.pattern.CharPredicates.isChar;
import static org.jparsec.pattern.CharPredicates.not;
import static org.jparsec.pattern.CharPredicates.or;
import static org.jparsec.pattern.CharPredicates.range;

public class GedcomPredicates {

    public static CharPredicate otherCharPredicate() {
        return or(
                isChar('"'),
                isChar('!'),
                range('$', '/'),
                range(':', '?'),
                range('[', '^'),
                isChar('`'),
                range('{', '~'),
                CharPredicates.range((char)128, (char)255)
        );
    }

    public static CharPredicate nonAtPredicate() {
        return and(not(isChar('@')), not(reservedCharacters()),
                or(
                        IS_ALPHA_NUMERIC_,
                        otherCharPredicate(),
                        isChar('#'),
                        isChar(' ')
                )
        );
    }


    private static CharPredicate reservedCharacters() {
        return or(isChar(':'), isChar('!'));
    }


}
