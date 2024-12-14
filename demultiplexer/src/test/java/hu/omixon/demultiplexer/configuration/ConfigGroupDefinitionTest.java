package hu.omixon.demultiplexer.configuration;
import hu.omixon.demultiplexer.configuration.rule.BestRule;
import hu.omixon.demultiplexer.configuration.rule.EndsRule;
import hu.omixon.demultiplexer.configuration.rule.MidRule;
import hu.omixon.demultiplexer.configuration.rule.RuleParams;
import hu.omixon.demultiplexer.sequence.NucleotideBase;
import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigGroupDefinitionTest {


    @Test
    void testConfigGroupDefinitionWithEndsAlignment() {
        String groupName = "TestGroup";
        Allignment alignment = Allignment.ENDS;
        RuleParams ruleParams = getRuleParams(true, true, false);

        ConfigGroupDefinition configGroupDefinition = new ConfigGroupDefinition(groupName, alignment, ruleParams);

        assertInstanceOf(EndsRule.class, configGroupDefinition.getConfigRule(), "Expected EndsRule for ENDS alignment");
        assertEquals(ruleParams.prefix(), ((EndsRule) configGroupDefinition.getConfigRule()).prefix(), "Prefix is passed to rule");
        assertEquals(ruleParams.postfix(), ((EndsRule) configGroupDefinition.getConfigRule()).postfix(), "Postfix is passed to rule");

    }

    @Test
    void testConfigGroupDefinitionWithMidAlignment() {
        String groupName = "TestGroup";
        Allignment alignment = Allignment.MID;
        RuleParams ruleParams = getRuleParams(false, false, true);

        ConfigGroupDefinition configGroupDefinition = new ConfigGroupDefinition(groupName, alignment, ruleParams);

        assertInstanceOf(MidRule.class, configGroupDefinition.getConfigRule(), "Expected MidRule for MID alignment");
        assertEquals(ruleParams.infix(), ((MidRule) configGroupDefinition.getConfigRule()).infix(), "Infix is passed to rule");
    }

    @Test
    void testConfigGroupDefinitionWithBestAlignment() {
        String groupName = "TestGroup";
        Allignment alignment = Allignment.BEST;
        RuleParams ruleParams = getRuleParams(false, false, true);

        ConfigGroupDefinition configGroupDefinition = new ConfigGroupDefinition(groupName, alignment, ruleParams);

        assertInstanceOf(BestRule.class, configGroupDefinition.getConfigRule(), "Expected BestRule for BEST alignment");
        assertEquals(ruleParams.infix(), ((BestRule) configGroupDefinition.getConfigRule()).infix(), "Infix is passed to rule");
    }

    @Test
    void testConfigGroupDefinitionWithNullAlignment() {
        String groupName = "TestGroup";
        RuleParams ruleParams = getRuleParams(false, false, false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ConfigGroupDefinition(groupName, null, ruleParams);
        });
        assertEquals("Allignment cannot be null", exception.getMessage());
    }

    private static RuleParams getRuleParams(boolean includePrefix, boolean includePostFix, boolean includeInfix) {
        Sequence prefix = includePrefix ? new Sequence(List.of(NucleotideBase.CYTOSINE, NucleotideBase.ADENINE)) : null;
        Sequence postfix = includePostFix ? new Sequence(List.of(NucleotideBase.THYMINE, NucleotideBase.GUANINE)) : null;
        Sequence infix = includeInfix ? new Sequence(List.of(NucleotideBase.GUANINE)) : null;
        return new RuleParams(prefix, postfix, infix);
    }

}