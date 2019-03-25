package net.biville.florent.gedcom.v55;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecordTransformer implements Function<List<Line>, List<Record>> {

    private final Function<List<Line>, List<Line>> lineTransformer;

    public RecordTransformer(Function<List<Line>, List<Line>> lineTransformer) {
        this.lineTransformer = lineTransformer;
    }

    @Override
    public List<Record> apply(List<Line> lines) {
        return validate(lineTransformer.apply(lines).stream().map(Record::new).collect(Collectors.toList()));
    }

    // TODO: validate cross-references (ie no dangling pointer)
    // TODO: validate encoding
    // TODO: validate records occupy at most 32K (as per spec)???
    private List<Record> validate(List<Record> records) {
        if (records.isEmpty() || records.get(0).getTag() != StandardTag.HEAD) {
            throw new IllegalArgumentException("transmission must start with HEAD");
        }
        if (records.get(records.size() - 1).getTag() != StandardTag.TRLR) {
            throw new IllegalArgumentException("transmission must end with TRLR");
        }
        return records;
    }
}
