package hu.omixon.demultiplexer.configuration.strategy;

import hu.omixon.demultiplexer.configuration.ConfigGroupDefinition;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.Sequence;
import hu.omixon.demultiplexer.sequence.SequenceSample;

import java.util.Comparator;
import java.util.List;

public class BestMatchGroupingStrategy implements GroupingStrategy {

    @Override
    public DemultiplexerResult splitSequenceToGroups(SequenceSample sample, List<ConfigGroupDefinition> groupDefinitions) {

        if (sample == null || groupDefinitions == null) {
            throw new IllegalArgumentException("Both sample and groupDefinitions must be provided");
        }

        DemultiplexerResult result = new DemultiplexerResult();

        for (ConfigGroupDefinition group : groupDefinitions) {

            Sequence bestMatch = null;
            int highestMatchValue = 0; // Initialize to 0 to only allow positive matches

            for (Sequence sequence : sample.sequences()) {
                int matchValue = group.getMatchValue(sequence);

                if (matchValue > highestMatchValue) {
                    highestMatchValue = matchValue;
                    bestMatch = sequence;
                }
            }

            result.addResult(group.groupName(), bestMatch);
        }

        result.collectUnmatchedSequences(sample.sequences());
        return result;

    }

}
