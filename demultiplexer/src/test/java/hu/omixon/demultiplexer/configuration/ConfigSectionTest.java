package hu.omixon.demultiplexer.configuration;

import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.configuration.strategy.BestMatchGroupingStrategy;
import hu.omixon.demultiplexer.configuration.strategy.FirstHitGroupingStrategy;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;

class ConfigSectionTest {

    @Test
    void testInitStrategyWithBestAlignment() {
        Allignment alignment = Allignment.BEST;

        ConfigSection configSection = new ConfigSection(alignment);

        assertInstanceOf(BestMatchGroupingStrategy.class, configSection.getGroupingStrategy(),
                "Expected BestMatchGroupingStrategy for BEST alignment");
    }

    @Test
    void testInitStrategyWithEndsAlignment() {
        Allignment alignment = Allignment.ENDS;

        ConfigSection configSection = new ConfigSection(alignment);

        assertInstanceOf(FirstHitGroupingStrategy.class, configSection.getGroupingStrategy(),
                "Expected FirstHitGroupingStrategy for ENDS alignment");
    }

    @Test
    void testInitStrategyWithMidAlignment() {
        Allignment alignment = Allignment.MID;

        ConfigSection configSection = new ConfigSection(alignment);

        assertInstanceOf(FirstHitGroupingStrategy.class, configSection.getGroupingStrategy(),
                "Expected FirstHitGroupingStrategy for MID alignment");
    }

    @Test
    void testSplitSequenceToGroupsDelegatesToStrategy() {
        ConfigSection configSection = new ConfigSection(Allignment.BEST);
        SequenceSample mockSample = mock(SequenceSample.class);
        ConfigGroupDefinition mockDefinition = mock(ConfigGroupDefinition.class);
        configSection.addGroupDefinition(mockDefinition);

        DemultiplexerResult resultOne = configSection.splitSequenceToGroups(mockSample);
        DemultiplexerResult resultTwo = configSection.getGroupingStrategy().splitSequenceToGroups(mockSample, configSection.getGroupDefinitions());

        assertEquals(resultOne.groups().size(), resultTwo.groups().size());
    }

}
