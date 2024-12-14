package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public record EndsRule(Sequence prefix, Sequence postfix) implements ConfigRule {

    @Override
    public int getMatchValue(Sequence sequence) {
        // TODO
        return 1;
    }

}
