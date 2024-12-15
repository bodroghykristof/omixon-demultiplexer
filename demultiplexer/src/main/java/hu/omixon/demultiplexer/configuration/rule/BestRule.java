package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public record BestRule(Sequence infix) implements ConfigRule {

    public BestRule {
        if (infix == null) {
            throw new IllegalArgumentException("Infix must be defined");
        }
    }

    @Override
    public int getMatchValue(Sequence sequence) {
        return 1;
    }

}
