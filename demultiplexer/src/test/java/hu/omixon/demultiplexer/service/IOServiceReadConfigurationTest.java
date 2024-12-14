package hu.omixon.demultiplexer.service;

import com.fasterxml.jackson.core.JsonParseException;
import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.configuration.ConfigSection;
import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.util.ResourceUtil;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IOServiceReadConfigurationTest {

    private final IOService ioService = new IOService();

    private static final String RESOURCE_DIR = "read_configuration";

    @Test
    void testReadConfiguration_NotExistingFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/no_such_file.conf");

        assertThrows(FileNotFoundException.class, () -> ioService.readConfiguration(absolutePath));
    }

    @Test
    void testReadConfiguration_NotJsonFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/not_json.conf");

        assertThrows(JsonParseException.class, () -> ioService.readConfiguration(absolutePath));
    }

    @Test
    void testReadConfiguration_EmptyFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/empty.conf");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ioService.readConfiguration(absolutePath));
        assertEquals("Configuration sections cannot be empty", exception.getMessage());
    }

    @Test
    void testReadConfiguration_EmptyObject() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/empty_obj.conf");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ioService.readConfiguration(absolutePath));
        assertEquals("Configuration sections cannot be empty", exception.getMessage());
    }

    @Test
    void testReadConfiguration_OnlyForeignFieldsObject() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/only_foreign_fields.conf");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ioService.readConfiguration(absolutePath));
        assertEquals("Configuration sections cannot be empty", exception.getMessage());
    }

    @Test
    void testReadConfiguration_NormalObject() throws IOException {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/normal_config.conf");

        DemultiplexerConfiguration configuration = ioService.readConfiguration(absolutePath);

        ConfigSection endsSection = configuration.findSectionByAllignment(Allignment.ENDS);
        ConfigSection midSection = configuration.findSectionByAllignment(Allignment.MID);
        ConfigSection bestSection = configuration.findSectionByAllignment(Allignment.BEST);

        assertNotNull(endsSection);
        assertNotNull(midSection);
        assertNotNull(bestSection);

        assertEquals(2, endsSection.getGroupDefinitions().size());
        assertEquals(2, midSection.getGroupDefinitions().size());
        assertEquals(1, bestSection.getGroupDefinitions().size());

    }

}