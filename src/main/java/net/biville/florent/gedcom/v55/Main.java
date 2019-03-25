package net.biville.florent.gedcom.v55;

import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Exception {
        PointerParserSupplier pointerParserSupplier = new PointerParserSupplier();
        LineParserSupplier lineParserSupplier = new LineParserSupplier(
                new LevelParserSupplier(),
                new CrossReferenceIdParserSupplier(pointerParserSupplier),
                new TagParserSupplier(),
                new LineValueParserSupplier(pointerParserSupplier)
        );

        LineTransformer lineTransformer = new LineTransformer();
        RecordTransformer recordTransformer = new RecordTransformer(lineTransformer);
        TransmissionParser transmissionParser = new TransmissionParser(lineParserSupplier.get(), recordTransformer);

        Transmission transmission = transmissionParser.parse(new FileReader(args[0]));
        System.out.println(transmission);
    }
}
