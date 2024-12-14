package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

@FunctionalInterface
public interface ConfigRule {

    int getMatchValue(Sequence sequence);

}
