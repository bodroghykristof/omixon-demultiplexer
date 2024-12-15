package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EndsRuleTest {

    @Test
    void testConstructor_WithEmptyPrefix() {
        Sequence prefix = Sequence.fromBaseChain("ATC");
        Sequence postfix = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new EndsRule(prefix, postfix));

        assertEquals("Both prefix and postfix must be defined", exception.getMessage());
    }

    @Test
    void testConstructor_WithEmptyPostfix() {
        Sequence prefix = null;
        Sequence postfix = Sequence.fromBaseChain("ATC");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new EndsRule(prefix, postfix));

        assertEquals("Both prefix and postfix must be defined", exception.getMessage());
    }

    @Test
    void testGetMatchValue_WithNullSequence() {
        Sequence prefix = Sequence.fromBaseChain("ATC");
        Sequence postfix = Sequence.fromBaseChain("GTC");
        EndsRule endsRule = new EndsRule(prefix, postfix);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> endsRule.getMatchValue(null));

        assertEquals("Cannot run rule on null sequence", exception.getMessage());
    }

    @Test
    void testGetMatchValue_WithNoMatch() {
        Sequence sequence = Sequence.fromBaseChain("ACTCACGACCACTAACTAGCAATACGATCG");
        Sequence prefix = Sequence.fromBaseChain("ATC");
        Sequence postfix = Sequence.fromBaseChain("GTC");

        EndsRule endsRule = new EndsRule(prefix, postfix);

        assertEquals(0, endsRule.getMatchValue(sequence));
    }

    @Test
    void testGetMatchValue_WithMatchingPrefixOnly() {
        Sequence sequence = Sequence.fromBaseChain("ACTCACGACCACTAACTAGCAATACGATCG");
        Sequence prefix = Sequence.fromBaseChain("ACT");
        Sequence postfix = Sequence.fromBaseChain("GTC");

        EndsRule endsRule = new EndsRule(prefix, postfix);

        assertEquals(0, endsRule.getMatchValue(sequence));
    }

    @Test
    void testGetMatchValue_WithMatchingPostfixOnly() {
        Sequence sequence = Sequence.fromBaseChain("ACTCACGACCACTAACTAGCAATACGATCG");
        Sequence prefix = Sequence.fromBaseChain("TGC");
        Sequence postfix = Sequence.fromBaseChain("TCG");

        EndsRule endsRule = new EndsRule(prefix, postfix);

        assertEquals(0, endsRule.getMatchValue(sequence));
    }

    @Test
    void testGetMatchValue_WithLongerPatternsThanActualSequence() {
        Sequence sequence = Sequence.fromBaseChain("ACTG");
        Sequence prefix = Sequence.fromBaseChain("GGTCACACTT");
        Sequence postfix = Sequence.fromBaseChain("AGGCTAGAT");

        EndsRule endsRule = new EndsRule(prefix, postfix);

        assertEquals(0, endsRule.getMatchValue(sequence));
    }

    @Test
    void testGetMatchValue_WithMatch() {
        Sequence sequence = Sequence.fromBaseChain("ACTCACGACCACTAACTAGCAATACGATCG");
        Sequence prefix = Sequence.fromBaseChain("ACT");
        Sequence postfix = Sequence.fromBaseChain("TCG");

        EndsRule endsRule = new EndsRule(prefix, postfix);

        assertEquals(1, endsRule.getMatchValue(sequence));
    }


}