package hu.omixon.demultiplexer.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemultiplexerConfigurationTest {

    private DemultiplexerConfiguration configuration;

    @BeforeEach
    void setUp() {
        configuration = new DemultiplexerConfiguration();
    }

    @Test
    void testAddSection_AddsSectionSuccessfully() {
        ConfigSection section = new ConfigSection(Allignment.ENDS);

        configuration.addSection(section);

        // Verify the section is added
        assertTrue(configuration.getSections().contains(section));
    }

    @Test
    void testAddMultipleSections_AddsAllSectionsSuccessfully() {
        ConfigSection section1 = new ConfigSection(Allignment.ENDS);
        ConfigSection section2 = new ConfigSection(Allignment.MID);
        ConfigSection section3 = new ConfigSection(Allignment.MID);

        configuration.addSection(section1);
        configuration.addSection(section2);
        configuration.addSection(section3);

        // Verify all sections are added
        assertTrue(configuration.getSections().contains(section1));
        assertTrue(configuration.getSections().contains(section2));
        assertTrue(configuration.getSections().contains(section3));
        assertEquals(3, configuration.getSections().size());
    }

    @Test
    void testFindSectionByAllignment_NullInputThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            configuration.findSectionByAllignment(null);
        });
        assertEquals("Allignment cannot be null", exception.getMessage());
    }

    @Test
    void testFindSectionByAllignment_SuccessfulRetrieval() {
        ConfigSection section = new ConfigSection(Allignment.ENDS);

        configuration.addSection(section);

        ConfigSection result = configuration.findSectionByAllignment(Allignment.ENDS);

        assertNotNull(result);
        assertEquals(section, result);
    }

    @Test
    void testFindSectionByAllignment_IllegalArgumentNotFound() {
        ConfigSection section = new ConfigSection(Allignment.ENDS);
        configuration.addSection(section);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            configuration.findSectionByAllignment(Allignment.MID);
        });
        assertEquals("No " + Allignment.MID + "section was found in configuration", exception.getMessage());
    }

}
