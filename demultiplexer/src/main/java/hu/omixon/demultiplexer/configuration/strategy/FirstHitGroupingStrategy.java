package hu.omixon.demultiplexer.configuration.strategy;

import hu.omixon.demultiplexer.configuration.ConfigGroupDefinition;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.SequenceSample;

import java.util.List;

public class FirstHitGroupingStrategy implements GroupingStrategy {

    @Override
    public DemultiplexerResult splitSequenceToGroups(SequenceSample sample, List<ConfigGroupDefinition> groupDefinitions) {
        // TODO
        return null;
    }

}
