package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public record EndsRule(Sequence prefix, Sequence postfix) implements ConfigRule {

    public EndsRule {
        if (prefix == null || postfix == null) {
            throw new IllegalArgumentException("Both prefix and postfix must be defined");
        }
    }

    @Override
    public int getMatchValue(Sequence sequence) {

        if (sequence == null) {
            throw new IllegalArgumentException("Cannot run rule on null sequence");
        }

        String prefixAsString = this.prefix.toBaseChainString();
        String postfixAsString = this.postfix.toBaseChainString();
        String sequenceAsString = sequence.toBaseChainString();

        return sequenceAsString.startsWith(prefixAsString) && sequenceAsString.endsWith(postfixAsString) ? 1 : 0;
    }

}
