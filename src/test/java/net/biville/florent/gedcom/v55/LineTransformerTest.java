package net.biville.florent.gedcom.v55;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class LineTransformerTest {

    private Function<List<Line>, List<Line>> lineTreeTransformer = new LineTransformer();

    @Test
    @DisplayName("partitions single line")
    void partitions_single_line() {
        Line single = line(0);

        List<Line> partition = lineTreeTransformer.apply(singletonList(single));

        assertThat(partition)
                .containsExactly(single);
    }

    @Test
    @DisplayName("partitions independent lines")
    void partitions_independent_lines() {
        Line first = line(0);
        Line second = line(0);

        List<Line> partition = lineTreeTransformer.apply(asList(first, second));

        assertThat(partition).containsExactly(first, second);
    }

    @Test
    @DisplayName("partitions nested lines")
    void partitions_nested_lines() {
        Line root = line(0);
        Line child = line(1);

        List<Line> partition = lineTreeTransformer.apply(asList(root, child));

        assertThat(partition).containsExactly(root.setChild(child));
    }

    @Test
    @DisplayName("partitions independent children")
    void partitions_independent_children() {
        Line root = line(0);
        Line child1 = line(1);
        Line child2 = line(1);

        List<Line> partition = lineTreeTransformer.apply(asList(root, child1, child2));

        assertThat(partition)
                .containsExactly(
                        root.setChild(child1).setChild(child2)
                );
    }

    @Test
    @DisplayName("partitions independent children with descendants")
    void partitions_independent_children_with_descendants() {
        Line root = line(0);
        Line child1 = line(1);
        Line grandchild11 = line(2);
        Line greatgrandchild111 = line(3);
        Line child2 = line(1);
        List<Line> partition = lineTreeTransformer.apply(asList(root, child1, grandchild11, greatgrandchild111, child2));

        assertThat(partition)
                .containsExactly(
                        root.setChild(child1.setChild(grandchild11.setChild(greatgrandchild111))).setChild(child2)
                );
    }

    @Test
    @DisplayName("partitions empty lines")
    void partitions_empty_lines() {
        List<Line> partition = lineTreeTransformer.apply(emptyList());

        assertThat(partition).isEmpty();
    }

    @Test
    @DisplayName("partitions independent lines with children")
    void partitions_independent_lines_with_children() {
        Line root1 = line(0);
        Line root1child1 = line(1);
        Line grandchild1 = line(2);
        Line root2 = line(0);
        Line root2child1 = line(1);

        List<Line> partition = lineTreeTransformer.apply(asList(root1, root1child1, grandchild1, root2, root2child1));

        assertThat(partition)
                .containsExactly(
                        root1.setChild(root1child1.setChild(grandchild1)),
                        root2.setChild(root2child1)
                );
    }

    @Test
    @DisplayName("partitions independent lines with several levels of siblings")
    void partitions_independent_lines_with_siblings() {
        Line root1 = line(0);
        Line root1child1 = line(1);
        Line root1child2 = line(1);
        Line root1child3 = line(1);
        Line grandchild31 = line(2);
        Line grandchild32 = line(2);

        List<Line> partition = lineTreeTransformer.apply(asList(root1, root1child1, root1child2, root1child3, grandchild31, grandchild32));

        assertThat(partition)
                .containsExactly(
                        root1.setChild(
                                root1child1)
                                .setChild(root1child2)
                                .setChild(root1child3
                                        .setChild(grandchild31)
                                        .setChild(grandchild32))
                );
    }

    @Test
    @DisplayName("fails to partition with invalid root line")
    void fails_to_partition_invalid_root_line() {
        assertThatCode(() -> lineTreeTransformer.apply(singletonList(line(1))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sequence should start with level zero");
    }

    @Test
    @DisplayName("fails to partition non incremental nested lines")
    void fails_to_partition_non_incremental_nested_lines() {
        assertThatCode(() -> lineTreeTransformer.apply(asList(line(0), line(2))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("level 2 cannot be immediately after level 0");
    }


    private Line line(int level) {
        return new Line(new Level(level), Optional.empty(), StandardTag.ADR1, Optional.empty());
    }
}
