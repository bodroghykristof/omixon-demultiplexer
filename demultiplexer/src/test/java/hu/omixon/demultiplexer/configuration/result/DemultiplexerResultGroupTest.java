package hu.omixon.demultiplexer.configuration.result;

import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DemultiplexerResultGroupTest {

    @Test
    void testAddSequence() {
        DemultiplexerResultGroup group = new DemultiplexerResultGroup("Group1");
        Sequence sequence = Sequence.fromBaseChain("ACGT");

        group.addSequence(sequence);

        assertNotNull(group.getSequences());
        assertEquals(1, group.getSequences().size());
        assertEquals(sequence, group.getSequences().getFirst());
    }

    @Test
    void testAddSequenceToExistingList() {
        DemultiplexerResultGroup group = new DemultiplexerResultGroup("Group1");
        Sequence sequence1 = Sequence.fromBaseChain("ACGT");
        Sequence sequence2 = Sequence.fromBaseChain("TGCA");
        group.addSequence(sequence1);

        group.addSequence(sequence2);

        assertNotNull(group.getSequences());
        assertEquals(2, group.getSequences().size());
        assertEquals(sequence1, group.getSequences().get(0));
        assertEquals(sequence2, group.getSequences().get(1));
    }

}