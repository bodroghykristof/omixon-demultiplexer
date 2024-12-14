package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public class BestRule implements ConfigRule {

    @Override
    public int getMatchValue(Sequence sequence) {
        return 1;
    }

}
