package hu.omixon.demultiplexer.configuration.result;

import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DemultiplexerResultTest {

    private DemultiplexerResult result;

    @BeforeEach
    public void setUp() {
        result = new DemultiplexerResult();
    }

    @Test
    void testAddResultNewGroup() {
        Sequence sequence = Sequence.fromBaseChain("ACGT");

        result.addResult("Group1", sequence);

        assertNotNull(result.getGroups());
        assertEquals(1, result.getGroups().size());
        assertEquals("Group1", result.getGroups().getFirst().getGroupName());
        assertEquals(sequence, result.getGroups().getFirst().getSequences().getFirst());
    }

    @Test
    void testAddResultToExistingGroup() {
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

        int groupCount = result.countGroups();

        assertEquals(0, groupCount);
    }

    @Test
    void testCountGroups() {
        Sequence sequence1 = Sequence.fromBaseChain("ACGT");
        Sequence sequence2 = Sequence.fromBaseChain("TGCA");
        result.addResult("Group1", sequence1);
        result.addResult("Group2", sequence2);

        int groupCount = result.countGroups();

        assertEquals(2, groupCount);
    }

    @Test
    void testFindGroupByName_WithNullParameter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> result.findGroupByName(null));
        assertEquals("GroupName cannot ba null", exception.getMessage());
    }

    @Test
    void testFindGroupByName_WithEmptyGroups() {
        assertTrue(result.findGroupByName("GroupName").isEmpty());
    }

    @Test
    void testFindGroupByName_WithNoMatch() {
        Sequence sequence = Sequence.fromBaseChain("ACGT");
        result.addResult("Group1", sequence);

        assertTrue(result.findGroupByName("GroupName").isEmpty());
    }

    @Test
    void testFindGroupByName_WithMatch() {
        Sequence sequence = Sequence.fromBaseChain("ACGT");
        String groupName = "Group1";
        result.addResult(groupName, sequence);

        Optional<DemultiplexerResultGroup> group = result.findGroupByName(groupName);
        assertTrue(group.isPresent());
        assertEquals(groupName, group.get().getGroupName());
    }

}