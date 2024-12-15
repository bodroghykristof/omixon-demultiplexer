package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public record MidRule(Sequence infix) implements ConfigRule {

    public MidRule {
        if (infix == null) {
            throw new IllegalArgumentException("Infix must be defined");
        }
    }

    @Override
    public int getMatchValue(Sequence sequence) {

        if (sequence == null) {
            throw new IllegalArgumentException("Cannot run rule on null sequence");
        }

        String infixAsString = this.infix.toBaseChainString();
        String sequenceAsString = sequence.toBaseChainString();

        return sequenceAsString.contains(infixAsString) ? 1 : 0;

    }

}
