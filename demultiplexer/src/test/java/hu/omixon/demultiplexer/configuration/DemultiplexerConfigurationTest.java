package hu.omixon.demultiplexer.configuration;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DemultiplexerConfigurationTest {


    @Test
    void testFindSectionByAllignment_NullInputThrowsException() {
        DemultiplexerConfiguration configuration = createConfiguration();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> configuration.findSectionByAllignment(null));
        assertEquals("Allignment cannot be null", exception.getMessage());
    }

    @Test
    void testFindSectionByAllignment_SuccessfulRetrieval() {
        DemultiplexerConfiguration configuration = createConfiguration();

        ConfigSection result = configuration.findSectionByAllignment(Allignment.ENDS);

        assertNotNull(result);
        assertEquals(configuration.sections().getFirst(), result);
    }

    @Test
    void testFindSectionByAllignment_IllegalArgumentNotFound() {
        DemultiplexerConfiguration configuration = createConfiguration();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> configuration.findSectionByAllignment(Allignment.MID));
        assertEquals("No " + Allignment.MID + "section was found in configuration", exception.getMessage());
    }

    private static DemultiplexerConfiguration createConfiguration() {
        ConfigSection section = new ConfigSection(Allignment.ENDS);
        return new DemultiplexerConfiguration(List.of(section));
    }

}
