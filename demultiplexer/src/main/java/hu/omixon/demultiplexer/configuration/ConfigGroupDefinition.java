package hu.omixon.demultiplexer.configuration;

import hu.omixon.demultiplexer.configuration.rule.ConfigRule;
import hu.omixon.demultiplexer.sequence.Sequence;

public record ConfigGroupDefinition(String groupName, ConfigRule configRule) {

    public ConfigGroupDefinition {
        if (configRule == null) {
            throw new IllegalArgumentException("Config rule must not be null");
        }
    }

    public int getMatchValue(Sequence sequence) {
        return this.configRule.getMatchValue(sequence);
    }

    public boolean isMatch(Sequence sequence) {
        return this.configRule.getMatchValue(sequence) > 0;
    }
    
}
