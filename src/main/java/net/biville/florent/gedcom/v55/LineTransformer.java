package net.biville.florent.gedcom.v55;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

public class LineTransformer implements Function<List<Line>, List<Line>> {

    @Override
    public List<Line> apply(List<Line> lines) {
        if (lines.isEmpty()) {
            return emptyList();
        }
        List<Line> result = new ArrayList<>(lines.size());
        Line first = lines.get(0);
        if (first.getLevelValue() != 0) {
            throw new IllegalArgumentException("sequence should start with level zero");
        }
        result.add(first);
        Line previous = first;
        int recordStartIndex = 0;
        for (int lineIndex = 1; lineIndex < lines.size(); lineIndex++) {
            Line current = lines.get(lineIndex);
            int currentId = current.getLevelValue();
            if (currentId == 0) {
                recordStartIndex = lineIndex;
                result.add(current);
            } else {
                int previousId = previous.getLevelValue();
                if (currentId > previousId + 1) {
                    throw new IllegalArgumentException(String.format("level %d cannot be immediately after level %d", currentId, previousId));
                }
                if (previousId + 1 == currentId) {
                    previous.setChild(current);
                } else {
                    int parentIndex = IntStream.rangeClosed(recordStartIndex, lineIndex)
                            .filter(i -> lines.get(i).getLevelValue() + 1 == currentId)
                            .reduce((f, s) -> s) // take last
                            .getAsInt();
                    lines.get(parentIndex).setChild(current);
                }
            }
            previous = current;
        }
        return result;
    }
}
