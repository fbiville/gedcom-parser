package net.biville.florent.gedcom.v55;

import org.jparsec.Parser;

import java.util.function.Supplier;

public class CrossReferenceIdParserSupplier implements Supplier<Parser<CrossReferenceId>> {

    private final Supplier<Parser<Void>> pointerParserSupplier;

    CrossReferenceIdParserSupplier() {
        this(new PointerParserSupplier());
    }

    public CrossReferenceIdParserSupplier(Supplier<Parser<Void>> pointerParserSupplier) {
        this.pointerParserSupplier = pointerParserSupplier;
    }

    @Override
    public Parser<CrossReferenceId> get() {
        return pointerParserSupplier.get().source().map(CrossReferenceId::new).label("CROSS-REFERENCE ID");
    }
}
