package hu.omixon.demultiplexer.configuration.strategy;

import hu.omixon.demultiplexer.configuration.ConfigGroupDefinition;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.Sequence;
import hu.omixon.demultiplexer.sequence.SequenceSample;

import java.util.List;

public class AllMatchGroupingStrategy implements GroupingStrategy {

    @Override
    public DemultiplexerResult splitSequenceToGroups(SequenceSample sample, List<ConfigGroupDefinition> groupDefinitions) {

        if (sample == null || groupDefinitions == null) {
            throw new IllegalArgumentException("Both sample and groupDefinitions must be provided");
        }

        DemultiplexerResult result = new DemultiplexerResult();

        for (Sequence sequence : sample.sequences()) {

            for (ConfigGroupDefinition groupDefinition : groupDefinitions) {
                if (groupDefinition.isMatch(sequence)) {
                    result.addResult(groupDefinition.groupName(), sequence);
                }
            }
        }

        return result;
    }

}
