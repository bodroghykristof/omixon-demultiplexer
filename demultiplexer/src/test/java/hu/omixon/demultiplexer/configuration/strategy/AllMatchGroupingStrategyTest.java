package hu.omixon.demultiplexer.configuration.strategy;

import hu.omixon.demultiplexer.configuration.ConfigGroupDefinition;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResultGroup;
import hu.omixon.demultiplexer.configuration.rule.ConfigRule;
import hu.omixon.demultiplexer.configuration.rule.MidRule;
import hu.omixon.demultiplexer.sequence.Sequence;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AllMatchGroupingStrategyTest {

    @BeforeAll
    public static void setUp() {
        strategy = new AllMatchGroupingStrategy();
    }

    private static AllMatchGroupingStrategy strategy;

    @Test
    void testSplitSequenceToGroups_WithNullSample() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> strategy.splitSequenceToGroups(null, Collections.emptyList()));
        assertEquals("Both sample and groupDefinitions must be provided", exception.getMessage());
    }

    @Test
    void testSplitSequenceToGroups_WithNullGroups() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> strategy.splitSequenceToGroups(new SequenceSample(Collections.emptyList()), null));
        assertEquals("Both sample and groupDefinitions must be provided", exception.getMessage());
    }

    @Test
    void testSplitSequenceToGroups_WithEmptySequences() {
        SequenceSample sequenceSample = new SequenceSample(Collections.emptyList());
        MidRule midRule = new MidRule(Sequence.fromBaseChain("ATCG"));
        List<ConfigGroupDefinition> groupDefinitions = List.of(new ConfigGroupDefinition("group_1", midRule));

        DemultiplexerResult result = strategy.splitSequenceToGroups(sequenceSample, groupDefinitions);

        assertEquals(0, result.countGroups());
    }

    @Test
    void testSplitSequenceToGroups_WithEmptyGroups() {
        SequenceSample sequenceSample = new SequenceSample(List.of(Sequence.fromBaseChain("ATCG"), Sequence.fromBaseChain("TGG")));

        DemultiplexerResult result = strategy.splitSequenceToGroups(sequenceSample, Collections.emptyList());

        assertEquals(0, result.countGroups());
    }

    @Test
    void testSplitSequenceToGroups() {

        Sequence sequenceOne = Sequence.fromBaseChain("ATC");
        Sequence sequenceTwo = Sequence.fromBaseChain("TTG");
        Sequence sequenceThree = Sequence.fromBaseChain("CCGT");
        Sequence sequenceFour = Sequence.fromBaseChain("GTAA");

        SequenceSample sequenceSample = new SequenceSample(List.of(sequenceOne, sequenceTwo, sequenceThree, sequenceFour));

        String groupNameOne = "GroupOne";
        ConfigRule mockConfigRuleOne = mock(ConfigRule.class);
        when(mockConfigRuleOne.getMatchValue(sequenceOne)).thenReturn(1);
        when(mockConfigRuleOne.getMatchValue(sequenceTwo)).thenReturn(0);
        when(mockConfigRuleOne.getMatchValue(sequenceThree)).thenReturn(0);
        when(mockConfigRuleOne.getMatchValue(sequenceFour)).thenReturn(1);
        ConfigGroupDefinition configGroupDefinitionOne = new ConfigGroupDefinition(groupNameOne, mockConfigRuleOne);

        String groupNameTwo = "GroupTwo";
        ConfigRule mockConfigRuleTwo = mock(ConfigRule.class);
        when(mockConfigRuleTwo.getMatchValue(sequenceOne)).thenReturn(0);
        when(mockConfigRuleTwo.getMatchValue(sequenceTwo)).thenReturn(1);
        when(mockConfigRuleTwo.getMatchValue(sequenceThree)).thenReturn(0);
        when(mockConfigRuleTwo.getMatchValue(sequenceFour)).thenReturn(1);
        ConfigGroupDefinition configGroupDefinitionTwo = new ConfigGroupDefinition(groupNameTwo, mockConfigRuleTwo);

        SequenceSample sample = new SequenceSample(List.of(sequenceOne, sequenceTwo, sequenceThree, sequenceFour));
        List<ConfigGroupDefinition> groupDefinitions = List.of(configGroupDefinitionOne, configGroupDefinitionTwo);

        DemultiplexerResult result = strategy.splitSequenceToGroups(sample, groupDefinitions);

        assertEquals(2, result.countGroups());

        Optional<DemultiplexerResultGroup> groupOne = result.findGroupByName(groupNameOne);
        assertTrue(groupOne.isPresent());
        assertEquals(2, groupOne.get().getSequences().size());
        assertTrue(groupOne.get().getSequences().containsAll(List.of(sequenceOne, sequenceFour)));

        Optional<DemultiplexerResultGroup> groupTwo = result.findGroupByName(groupNameTwo);
        assertTrue(groupTwo.isPresent());
        assertEquals(2, groupTwo.get().getSequences().size());
        assertTrue(groupTwo.get().getSequences().containsAll(List.of(sequenceTwo, sequenceFour)));

        List<Sequence> unmatchedSequences = result.collectUnmatchedSequences(sequenceSample.sequences());
        assertEquals(1, unmatchedSequences.size());
        assertEquals(sequenceThree, unmatchedSequences.getFirst());

    }

}