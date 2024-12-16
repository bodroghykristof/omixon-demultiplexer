package hu.omixon.demultiplexer.configuration.strategy;

import hu.omixon.demultiplexer.configuration.ConfigGroupDefinition;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.configuration.rule.MidRule;
import hu.omixon.demultiplexer.sequence.Sequence;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BestMatchGroupingStrategyTest {

    @BeforeAll
    public static void setUp() {
        strategy = new BestMatchGroupingStrategy();
    }

    private static BestMatchGroupingStrategy strategy;

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


}