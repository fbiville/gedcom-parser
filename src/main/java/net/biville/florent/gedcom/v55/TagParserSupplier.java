package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;
import org.jparsec.pattern.CharPredicates;
import org.jparsec.pattern.Pattern;
import org.jparsec.pattern.Patterns;

import java.util.EnumSet;
import java.util.Locale;
import java.util.function.Supplier;

public class TagParserSupplier implements Supplier<Parser<Tag>> {

    @Override
    public Parser<Tag> get() {
        return reservedGedcomTags().source().<Tag>map(s -> StandardTag.valueOf(s.toUpperCase(Locale.ENGLISH)))
                .or(customGedcomTag().source().map(CustomTag::new).label("custom tag"));
    }

    private Parser<Void> reservedGedcomTags() {
        return Patterns.longest(reservedGedcomTagNames()).toScanner("reserved tag");
    }

    private Parser<Void> customGedcomTag() {
        Parser<Void> underscore = Patterns.times(1, 1, CharPredicates.isChar('_')).toScanner("_");
        Parser<Void> alphanum_ = Patterns.times(1, 30, CharPredicates.IS_ALPHA_NUMERIC_).toScanner("ALPHANUM_");
        return underscore.followedBy(alphanum_);
    }

    private Pattern[] reservedGedcomTagNames() {
        return EnumSet.allOf(StandardTag.class).stream()
                .map(standardTag -> Patterns.stringCaseInsensitive(standardTag.name()))
                .toArray(Pattern[]::new);
    }
}
