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

class BestMatchGroupingStrategyTest {

    @BeforeAll
    public static void setUp() {

        strategy = new BestMatchGroupingStrategy();

        sequenceOne = Sequence.fromBaseChain("ATC");
        sequenceTwo = Sequence.fromBaseChain("TTG");
        sequenceThree = Sequence.fromBaseChain("CGC");
        sequenceFour = Sequence.fromBaseChain("ATT");
    }

    private static BestMatchGroupingStrategy strategy;

    private static Sequence sequenceOne;
    private static Sequence sequenceTwo;
    private static Sequence sequenceThree;
    private static Sequence sequenceFour;


    @Test
    void testSplitSequenceToGroups_WithNullSample() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> strategy.splitSequenceToGroups(null, Collections.emptyList()));
        assertEquals("Both sample and groupDefinitions must be provided", exception.getMessage());
    }

    @Test
    void testSplitSequenceToGroups_WithNullGroups() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> strategy.splitSequenceToGroups(SequenceSample.empty(), null));
        assertEquals("Both sample and groupDefinitions must be provided", exception.getMessage());
    }

    @Test
    void testSplitSequenceToGroups_WithEmptySequences() {
        SequenceSample sequenceSample = SequenceSample.empty();
        MidRule midRule = new MidRule(Sequence.fromBaseChain("ATCG"));
        List<ConfigGroupDefinition> groupDefinitions = List.of(new ConfigGroupDefinition("group_1", midRule));

        DemultiplexerResult result = strategy.splitSequenceToGroups(sequenceSample, groupDefinitions);

        assertEquals(0, result.countGroups());
    }

    @Test
    void testSplitSequenceToGroups_WithEmptyGroups() {
        SequenceSample sequenceSample = SequenceSample.of(Sequence.fromBaseChain("ATCG"), Sequence.fromBaseChain("TGG"));

        DemultiplexerResult result = strategy.splitSequenceToGroups(sequenceSample, Collections.emptyList());

        assertEquals(0, result.countGroups());
    }

    @Test
    void testSplitSequenceToGroups_WithNoUnmatchedSequences() {

        SequenceSample sequenceSample = SequenceSample.of(sequenceOne, sequenceTwo);

        String groupNameOne = "GroupOne";
        ConfigRule mockConfigRuleOne = mock(ConfigRule.class);
        when(mockConfigRuleOne.getMatchValue(sequenceOne)).thenReturn(5);
        when(mockConfigRuleOne.getMatchValue(sequenceTwo)).thenReturn(3);
        ConfigGroupDefinition configGroupDefinitionOne = new ConfigGroupDefinition(groupNameOne, mockConfigRuleOne);

        String groupNameTwo = "GroupTwo";
        ConfigRule mockConfigRuleTwo = mock(ConfigRule.class);
        when(mockConfigRuleTwo.getMatchValue(sequenceOne)).thenReturn(2);
        when(mockConfigRuleTwo.getMatchValue(sequenceTwo)).thenReturn(6);
        ConfigGroupDefinition configGroupDefinitionTwo = new ConfigGroupDefinition(groupNameTwo, mockConfigRuleTwo);

        SequenceSample sample = SequenceSample.of(sequenceOne, sequenceTwo);
        List<ConfigGroupDefinition> groupDefinitions = List.of(configGroupDefinitionOne, configGroupDefinitionTwo);

        DemultiplexerResult result = strategy.splitSequenceToGroups(sample, groupDefinitions);

        assertEquals(2, result.countGroups());

        Optional<DemultiplexerResultGroup> groupOne = result.findGroupByName(groupNameOne);
        assertTrue(groupOne.isPresent());
        assertEquals(1, groupOne.get().getSequences().size());
        assertEquals(groupOne.get().getSequences().getFirst(), sequenceOne);

        Optional<DemultiplexerResultGroup> groupTwo = result.findGroupByName(groupNameTwo);
        assertTrue(groupTwo.isPresent());
        assertEquals(1, groupTwo.get().getSequences().size());
        assertEquals(groupTwo.get().getSequences().getFirst(), sequenceTwo);

        assertEquals(0, result.collectUnmatchedSequences(sequenceSample.sequences()).size());

    }

    @Test
    void testSplitSequenceToGroups_WithUnmatchedSequences() {

        SequenceSample sequenceSample = SequenceSample.of(sequenceOne, sequenceTwo, sequenceThree, sequenceFour);

        String groupNameOne = "GroupOne";
        ConfigRule mockConfigRuleOne = mock(ConfigRule.class);
        when(mockConfigRuleOne.getMatchValue(sequenceOne)).thenReturn(5);
        when(mockConfigRuleOne.getMatchValue(sequenceTwo)).thenReturn(3);
        when(mockConfigRuleOne.getMatchValue(sequenceThree)).thenReturn(0);
        when(mockConfigRuleOne.getMatchValue(sequenceFour)).thenReturn(1);
        ConfigGroupDefinition configGroupDefinitionOne = new ConfigGroupDefinition(groupNameOne, mockConfigRuleOne);

        String groupNameTwo = "GroupTwo";
        ConfigRule mockConfigRuleTwo = mock(ConfigRule.class);
        when(mockConfigRuleTwo.getMatchValue(sequenceOne)).thenReturn(2);
        when(mockConfigRuleTwo.getMatchValue(sequenceTwo)).thenReturn(6);
        when(mockConfigRuleTwo.getMatchValue(sequenceThree)).thenReturn(0);
        when(mockConfigRuleTwo.getMatchValue(sequenceFour)).thenReturn(0);
        ConfigGroupDefinition configGroupDefinitionTwo = new ConfigGroupDefinition(groupNameTwo, mockConfigRuleTwo);

        SequenceSample sample = SequenceSample.of(sequenceOne, sequenceTwo, sequenceThree, sequenceFour);
        List<ConfigGroupDefinition> groupDefinitions = List.of(configGroupDefinitionOne, configGroupDefinitionTwo);

        DemultiplexerResult result = strategy.splitSequenceToGroups(sample, groupDefinitions);

        assertEquals(2, result.countGroups());

        Optional<DemultiplexerResultGroup> groupOne = result.findGroupByName(groupNameOne);
        assertTrue(groupOne.isPresent());
        assertEquals(1, groupOne.get().getSequences().size());
        assertEquals(groupOne.get().getSequences().getFirst(), sequenceOne);

        Optional<DemultiplexerResultGroup> groupTwo = result.findGroupByName(groupNameTwo);
        assertTrue(groupTwo.isPresent());
        assertEquals(1, groupTwo.get().getSequences().size());
        assertEquals(groupTwo.get().getSequences().getFirst(), sequenceTwo);

        List<Sequence> unmatchedSequences = result.collectUnmatchedSequences(sequenceSample.sequences());
        assertEquals(2, unmatchedSequences.size());
        assertTrue(unmatchedSequences.containsAll(List.of(sequenceThree, sequenceFour)));

    }

    @Test
    void testSplitSequenceToGroups_WithUnmatchedGroup() {

        SequenceSample sequenceSample = SequenceSample.of(sequenceOne, sequenceTwo, sequenceThree, sequenceFour);

        String groupNameOne = "GroupOne";
        ConfigRule mockConfigRuleOne = mock(ConfigRule.class);
        when(mockConfigRuleOne.getMatchValue(sequenceOne)).thenReturn(5);
        when(mockConfigRuleOne.getMatchValue(sequenceTwo)).thenReturn(3);
        when(mockConfigRuleOne.getMatchValue(sequenceThree)).thenReturn(0);
        when(mockConfigRuleOne.getMatchValue(sequenceFour)).thenReturn(1);
        ConfigGroupDefinition configGroupDefinitionOne = new ConfigGroupDefinition(groupNameOne, mockConfigRuleOne);

        String groupNameTwo = "GroupTwo";
        ConfigRule mockConfigRuleTwo = mock(ConfigRule.class);
        when(mockConfigRuleTwo.getMatchValue(sequenceOne)).thenReturn(0);
        when(mockConfigRuleTwo.getMatchValue(sequenceTwo)).thenReturn(0);
        when(mockConfigRuleTwo.getMatchValue(sequenceThree)).thenReturn(0);
        when(mockConfigRuleTwo.getMatchValue(sequenceFour)).thenReturn(0);
        ConfigGroupDefinition configGroupDefinitionTwo = new ConfigGroupDefinition(groupNameTwo, mockConfigRuleTwo);

        List<ConfigGroupDefinition> groupDefinitions = List.of(configGroupDefinitionOne, configGroupDefinitionTwo);

        DemultiplexerResult result = strategy.splitSequenceToGroups(sequenceSample, groupDefinitions);

        assertEquals(1, result.countGroups());

        Optional<DemultiplexerResultGroup> groupOne = result.findGroupByName(groupNameOne);
        assertTrue(groupOne.isPresent());
        assertEquals(1, groupOne.get().getSequences().size());
        assertEquals(groupOne.get().getSequences().getFirst(), sequenceOne);

        Optional<DemultiplexerResultGroup> groupTwo = result.findGroupByName(groupNameTwo);
        assertTrue(groupTwo.isEmpty());

        List<Sequence> unmatchedSequences = result.collectUnmatchedSequences(sequenceSample.sequences());
        assertEquals(3, unmatchedSequences.size());
        assertTrue(unmatchedSequences.containsAll(List.of(sequenceTwo, sequenceThree, sequenceFour)));

    }

}