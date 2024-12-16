package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.configuration.Allignment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConfigRuleFactory {

    public ConfigRule createConfigRule(RuleParams ruleParams, Allignment allignment) {
        return switch (allignment) {
            case ENDS -> new EndsRule(ruleParams.prefix(), ruleParams.postfix());
            case MID -> new MidRule(ruleParams.infix());
            case BEST -> new BestRule(ruleParams.infix());
        };
    }

}
