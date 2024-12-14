package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public record MidRule(Sequence infix) implements ConfigRule {

    @Override
    public int getMatchValue(Sequence sequence) {
        return 1;
    }

}
