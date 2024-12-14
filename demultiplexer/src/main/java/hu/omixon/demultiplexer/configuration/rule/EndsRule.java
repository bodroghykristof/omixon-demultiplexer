package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public class EndsRule implements ConfigRule {

    @Override
    public int getMatchValue(Sequence sequence) {
        // TODO
        return 1;
    }

}
