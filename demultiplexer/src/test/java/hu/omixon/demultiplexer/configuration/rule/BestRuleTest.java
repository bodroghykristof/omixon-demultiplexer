package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BestRuleTest {

    @Test
    void testConstructor_WithEmptyInfix() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new BestRule(null));

        assertEquals("Infix must be defined", exception.getMessage());
    }

    @Test
    void testConstructor_WithTooShortSequence() {
        Sequence sequence = Sequence.fromBaseChain("ACTG");
        Sequence infix = Sequence.fromBaseChain("GGTCACACTT");

        BestRule bestRule = new BestRule(infix);

        assertEquals(0, bestRule.getMatchValue(sequence));
    }


}