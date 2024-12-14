package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public class MidRule implements ConfigRule {

    @Override
    public int getMatchValue(Sequence sequence) {
        return 1;
    }

}
