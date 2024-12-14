package hu.omixon.demultiplexer.configuration;

import hu.omixon.demultiplexer.configuration.strategy.BestMatchGroupingStrategy;
import hu.omixon.demultiplexer.configuration.strategy.FirstHitGroupingStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ConfigSectionTest {

    @Test
    void testInitStrategyWithBestAlignment() {
        // Given
        Allignment alignment = Allignment.BEST;

        // When
        ConfigSection configSection = new ConfigSection(alignment);

        // Then
        assertInstanceOf(BestMatchGroupingStrategy.class, configSection.getGroupingStrategy(),
                "Expected BestMatchGroupingStrategy for BEST alignment");
    }

    @Test
    void testInitStrategyWithEndsAlignment() {
        // Given
        Allignment alignment = Allignment.ENDS;

        // When
        ConfigSection configSection = new ConfigSection(alignment);

        // Then
        assertInstanceOf(FirstHitGroupingStrategy.class, configSection.getGroupingStrategy(),
                "Expected FirstHitGroupingStrategy for ENDS alignment");
    }

    @Test
    void testInitStrategyWithMidAlignment() {
        // Given
        Allignment alignment = Allignment.MID;

        // When
        ConfigSection configSection = new ConfigSection(alignment);

        // Then
        assertInstanceOf(FirstHitGroupingStrategy.class, configSection.getGroupingStrategy(),
                "Expected FirstHitGroupingStrategy for MID alignment");
    }

}
