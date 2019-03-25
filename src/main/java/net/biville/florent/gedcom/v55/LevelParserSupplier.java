package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.pattern.CharPredicates;
import org.jparsec.pattern.Pattern;
import org.jparsec.pattern.Patterns;

import java.util.function.Supplier;

import static org.jparsec.pattern.CharPredicates.range;

public class LevelParserSupplier implements Supplier<Parser<Level>> {

    @Override
    public Parser<Level> get() {
        // TODO: ignore tab and extra line terminator
        Parser<Void> whitespaces = Patterns.atLeast(0, CharPredicates.isChar(' ')).toScanner("whitespaces");
        Parser<Integer> levelParser = levelPattern().toScanner("level 0 to 99").source().map(Integer::valueOf);
        return whitespaces.next(levelParser).map(Level::new).label("LEVEL");
    }

    private Pattern levelPattern() {
        Pattern leadingDigit = Patterns.times(1, 1, range('1', '9'));
        Pattern singleDigit = Patterns.times(1, 1, CharPredicates.IS_DIGIT);
        Pattern twoDigits = leadingDigit.next(singleDigit);
        return Patterns.or(singleDigit, twoDigits);
    }
}
