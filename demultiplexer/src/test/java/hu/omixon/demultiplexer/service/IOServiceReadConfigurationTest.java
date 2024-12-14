package hu.omixon.demultiplexer.service;

import com.fasterxml.jackson.core.JsonParseException;
import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.configuration.ConfigSection;
import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.util.ResourceUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IOServiceReadConfigurationTest {

    private final IOService ioService = new IOService();

    private static final String RESOURCE_DIR = "read_configuration/";

    @Test
    void testReadConfiguration_NotExistingFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/no_such_file.conf");

        assertThrows(FileNotFoundException.class, () -> ioService.readConfiguration(absolutePath));
    }

    @Test
    void testReadConfiguration_NotJsonFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "not_json.conf");

        assertThrows(JsonParseException.class, () -> ioService.readConfiguration(absolutePath));
    }

    @Test
    void testReadConfiguration_InvalidSequenceInConfig() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "invalid_sequence_in_config.conf");

        assertThrows(IllegalArgumentException.class, () -> ioService.readConfiguration(absolutePath));
    }

    @ParameterizedTest
    @MethodSource("provideFileNamesForEmptyConfigurationTests")
    void testReadConfiguration_InvalidFiles(String fileName) {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + fileName);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ioService.readConfiguration(absolutePath));
        assertEquals("Configuration sections cannot be empty", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideFileNamesForSuccessfulTests")
    void testReadConfiguration_SuccessfulCases(String fileName) throws IOException {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + fileName);

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

    @Test
    void testReadConfiguration_WithPartialConfig() throws IOException {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "incomplete_config.conf");

        DemultiplexerConfiguration configuration = ioService.readConfiguration(absolutePath);

        ConfigSection endsSection = configuration.findSectionByAllignment(Allignment.ENDS);
        ConfigSection bestSection = configuration.findSectionByAllignment(Allignment.BEST);

        assertNotNull(endsSection);
        assertNotNull(bestSection);

        assertThrows(IllegalArgumentException.class, () -> configuration.findSectionByAllignment(Allignment.MID));

    }

    private static Stream<String> provideFileNamesForEmptyConfigurationTests() {
        return Stream.of(
                "empty.conf",
                "empty_obj.conf",
                "only_foreign_fields.conf"
        );
    }

    private static Stream<String> provideFileNamesForSuccessfulTests() {
        return Stream.of(
                "normal_config.conf",
                "foreign_fields.conf"
        );
    }

}