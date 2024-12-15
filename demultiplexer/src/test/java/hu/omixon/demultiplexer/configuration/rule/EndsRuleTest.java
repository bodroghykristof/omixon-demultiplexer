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


}