package hu.omixon.demultiplexer.configuration;

import hu.omixon.demultiplexer.configuration.strategy.BestMatchGroupingStrategy;
import hu.omixon.demultiplexer.configuration.strategy.FirstHitGroupingStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

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

}
