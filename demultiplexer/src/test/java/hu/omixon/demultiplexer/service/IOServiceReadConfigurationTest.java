package hu.omixon.demultiplexer.service;

import com.fasterxml.jackson.core.JsonParseException;
import hu.omixon.demultiplexer.util.ResourceUtil;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IOServiceReadConfigurationTest {

    private final IOService ioService = new IOService();

    private static final String RESOURCE_DIR = "read_configuration";

    @Test
    void testReadSequences_NotExistingFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/no_such_file.conf");

        assertThrows(FileNotFoundException.class, () -> ioService.readConfiguration(absolutePath));
    }

    @Test
    void testReadSequences_NotJsonFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/not_json.conf");

        assertThrows(JsonParseException.class, () -> ioService.readConfiguration(absolutePath));
    }

}