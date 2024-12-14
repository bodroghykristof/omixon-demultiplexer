package hu.omixon.demultiplexer.configuration;

import hu.omixon.demultiplexer.configuration.rule.*;
import lombok.Getter;

@Getter
public class ConfigGroupDefinition {

    private final String groupName;
    private final Allignment allignment;
    private final ConfigRule configRule;

    public ConfigGroupDefinition(String groupName, Allignment allignment, RuleParams ruleParams) {
        this.groupName = groupName;
        this.allignment = allignment;
        this.configRule = initRule(allignment, ruleParams);
    }

    private ConfigRule initRule(Allignment allignment, RuleParams ruleParams) {

        if (allignment == null) {
            throw new IllegalArgumentException("Allignment cannot be null");
        }

        return switch (allignment) {
            case ENDS -> new EndsRule(ruleParams.prefix(), ruleParams.postfix());
            case MID -> new MidRule(ruleParams.infix());
            case BEST -> new BestRule(ruleParams.infix());
        };

    }
    
}
