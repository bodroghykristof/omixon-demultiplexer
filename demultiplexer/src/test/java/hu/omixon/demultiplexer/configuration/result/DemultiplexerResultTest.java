package hu.omixon.demultiplexer.configuration.result;

import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DemultiplexerResultTest {

    @Test
    void testAddResultNewGroup() {
        DemultiplexerResult result = new DemultiplexerResult();
        Sequence sequence = Sequence.fromBaseChain("ACGT");

        result.addResult("Group1", sequence);

        assertNotNull(result.getGroups());
        assertEquals(1, result.getGroups().size());
        assertEquals("Group1", result.getGroups().getFirst().getGroupName());
        assertEquals(sequence, result.getGroups().getFirst().getSequences().getFirst());
    }

    @Test
    void testAddResultToExistingGroup() {
        DemultiplexerResult result = new DemultiplexerResult();
        Sequence sequence1 = Sequence.fromBaseChain("ACGT");
        Sequence sequence2 = Sequence.fromBaseChain("TGCA");

        result.addResult("Group1", sequence1);
        result.addResult("Group1", sequence2);

        assertNotNull(result.getGroups());
        assertEquals(1, result.getGroups().size());
        assertEquals(2, result.getGroups().getFirst().getSequences().size());
        assertEquals(sequence1, result.getGroups().getFirst().getSequences().getFirst());
        assertEquals(sequence2, result.getGroups().getFirst().getSequences().get(1));
    }

    @Test
    void testAddResultMultipleGroups() {
        DemultiplexerResult result = new DemultiplexerResult();
        Sequence sequence1 = Sequence.fromBaseChain("ACGT");
        Sequence sequence2 = Sequence.fromBaseChain("TGCA");

        result.addResult("Group1", sequence1);
        result.addResult("Group2", sequence2);

        assertNotNull(result.getGroups());
        assertEquals(2, result.getGroups().size());
        assertEquals("Group1", result.getGroups().getFirst().getGroupName());
        assertEquals(sequence1, result.getGroups().getFirst().getSequences().getFirst());
        assertEquals("Group2", result.getGroups().get(1).getGroupName());
        assertEquals(sequence2, result.getGroups().get(1).getSequences().getFirst());

    }

    @Test
    void testCountGroupsEmpty() {
        DemultiplexerResult result = new DemultiplexerResult();

        int groupCount = result.countGroups();

        assertEquals(0, groupCount);
    }

    @Test
    void testCountGroups() {
        DemultiplexerResult result = new DemultiplexerResult();
        Sequence sequence1 = Sequence.fromBaseChain("ACGT");
        Sequence sequence2 = Sequence.fromBaseChain("TGCA");
        result.addResult("Group1", sequence1);
        result.addResult("Group2", sequence2);

        int groupCount = result.countGroups();

        assertEquals(2, groupCount);
    }

}