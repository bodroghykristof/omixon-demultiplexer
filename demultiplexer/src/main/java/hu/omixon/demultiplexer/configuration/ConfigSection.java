package hu.omixon.demultiplexer.configuration;

import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.configuration.strategy.BestMatchGroupingStrategy;
import hu.omixon.demultiplexer.configuration.strategy.AllMatchGroupingStrategy;
import hu.omixon.demultiplexer.configuration.strategy.GroupingStrategy;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @ToString
public class ConfigSection {

    private final Allignment allignment;
    private final GroupingStrategy groupingStrategy;

    private List<ConfigGroupDefinition> groupDefinitions;

    public ConfigSection(Allignment allignment) {
        this.allignment = allignment;
        this.groupingStrategy = initStrategy(allignment);
    }

    private GroupingStrategy initStrategy(Allignment allignment) {
        return allignment == Allignment.BEST ? new BestMatchGroupingStrategy() : new AllMatchGroupingStrategy();
    }

    public void addGroupDefinition(ConfigGroupDefinition groupDefinition) {

        if (this.groupDefinitions == null) {
            this.groupDefinitions = new ArrayList<>();
        }

        this.groupDefinitions.add(groupDefinition);

    }

    public DemultiplexerResult splitSequenceToGroups(SequenceSample sequenceSample) {
        return this.groupingStrategy.splitSequenceToGroups(sequenceSample, this.groupDefinitions);
    }

}
