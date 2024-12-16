package hu.omixon.demultiplexer.sequence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public record SequenceSample(List<Sequence> sequences) {

    public static SequenceSample of(Sequence... sequences) {
        return new SequenceSample(Arrays.asList(sequences));
    }

    public static SequenceSample empty() {
        return new SequenceSample(Collections.emptyList());
    }

}
