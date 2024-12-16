package hu.omixon.demultiplexer.configuration.result;

import hu.omixon.demultiplexer.sequence.Sequence;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @ToString @RequiredArgsConstructor
public final class DemultiplexerResultGroup {

    private final String groupName;
    private List<Sequence> sequences;

    public void addSequence(Sequence sequence) {
        if (this.sequences == null) {
            this.sequences = new ArrayList<>();
        }

        this.sequences.add(sequence);
    }

}
