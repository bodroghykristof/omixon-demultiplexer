package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MidRuleTest {

    @Test
    void testConstructor_WithEmptyInfix() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new MidRule(null));

        assertEquals("Infix must be defined", exception.getMessage());
    }

    @Test
    void testGetMatchValue_WithNoMatch() {
        Sequence sequence = Sequence.fromBaseChain("ACTCACGACCACTAACTAGCAATACGATCG");
        Sequence infix = Sequence.fromBaseChain("TTT");

        MidRule endsRule = new MidRule(infix);

        assertEquals(0, endsRule.getMatchValue(sequence));
    }


    @Test
    void testGetMatchValue_WithLongerInfixThanActualSequence() {
        Sequence sequence = Sequence.fromBaseChain("ACTG");
        Sequence infix = Sequence.fromBaseChain("GGTCACACTT");

        MidRule endsRule = new MidRule(infix);

        assertEquals(0, endsRule.getMatchValue(sequence));
    }

    @Test
    void testGetMatchValue_WithMatch() {
        Sequence sequence = Sequence.fromBaseChain("ACTCACGACCACTAACTAGCAATACGATCG");
        Sequence infix = Sequence.fromBaseChain("GACCACT");

        MidRule endsRule = new MidRule(infix);

        assertEquals(1, endsRule.getMatchValue(sequence));
    }


}