package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.sequence.NucleotideBase;
import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ConfigRuleFactoryTest {

    @Test
    void testCreateConfigRuleWithEndsAlignment() {
        Allignment alignment = Allignment.ENDS;
        RuleParams ruleParams = getRuleParams(true, true, false);

        ConfigRule configRule = ConfigRuleFactory.createConfigRule(ruleParams, alignment);

        assertInstanceOf(EndsRule.class, configRule, "Expected EndsRule for ENDS alignment");
        assertEquals(ruleParams.prefix(), ((EndsRule) configRule).prefix(), "Prefix is passed to rule");
        assertEquals(ruleParams.postfix(), ((EndsRule) configRule).postfix(), "Postfix is passed to rule");

    }

    @Test
    void testCreateConfigRuleWithMidAlignment() {
        Allignment alignment = Allignment.MID;
        RuleParams ruleParams = getRuleParams(false, false, true);

        ConfigRule configRule = ConfigRuleFactory.createConfigRule(ruleParams, alignment);


        assertInstanceOf(MidRule.class, configRule, "Expected MidRule for MID alignment");
        assertEquals(ruleParams.infix(), ((MidRule) configRule).infix(), "Infix is passed to rule");
    }

    @Test
    void testCreateConfigRuleWithBestAlignment() {
        Allignment alignment = Allignment.BEST;
        RuleParams ruleParams = getRuleParams(false, false, true);

        ConfigRule configRule = ConfigRuleFactory.createConfigRule(ruleParams, alignment);
        
        assertInstanceOf(BestRule.class, configRule, "Expected BestRule for BEST alignment");
        assertEquals(ruleParams.infix(), ((BestRule) configRule).infix(), "Infix is passed to rule");
    }

    private static RuleParams getRuleParams(boolean includePrefix, boolean includePostFix, boolean includeInfix) {
        Sequence prefix = includePrefix ? new Sequence(List.of(NucleotideBase.CYTOSINE, NucleotideBase.ADENINE)) : null;
        Sequence postfix = includePostFix ? new Sequence(List.of(NucleotideBase.THYMINE, NucleotideBase.GUANINE)) : null;
        Sequence infix = includeInfix ? new Sequence(List.of(NucleotideBase.GUANINE)) : null;
        return new RuleParams(prefix, postfix, infix);
    }

}