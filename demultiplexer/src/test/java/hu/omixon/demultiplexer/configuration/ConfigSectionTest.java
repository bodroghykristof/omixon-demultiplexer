package hu.omixon.demultiplexer.configuration;

import hu.omixon.demultiplexer.configuration.strategy.BestMatchGroupingStrategy;
import hu.omixon.demultiplexer.configuration.strategy.FirstHitGroupingStrategy;
import hu.omixon.demultiplexer.configuration.strategy.GroupingStrategy;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        GroupingStrategy mockStrategy = mock(GroupingStrategy.class);
        ConfigSection configSection = new ConfigSection(Allignment.BEST, mockStrategy);
        SequenceSample mockSample = mock(SequenceSample.class);
        ConfigGroupDefinition mockDefinition = mock(ConfigGroupDefinition.class);
        configSection.addGroupDefinition(mockDefinition);

        configSection.splitSequenceToGroups(mockSample);

        @SuppressWarnings("unchecked") ArgumentCaptor<List<ConfigGroupDefinition>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockStrategy, times(1)).splitSequenceToGroups(eq(mockSample), captor.capture());

        List<ConfigGroupDefinition> capturedList = captor.getValue();
        assertNotNull(capturedList, "The list of group definitions should not be null");
        assertEquals(1, capturedList.size(), "The list should contain exactly one element");
        assertEquals(mockDefinition, capturedList.getFirst(), "The first element should be the mockDefinition");
    }

}
