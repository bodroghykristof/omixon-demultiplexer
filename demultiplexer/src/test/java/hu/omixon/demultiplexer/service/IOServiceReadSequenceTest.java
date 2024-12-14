package hu.omixon.demultiplexer.service;

import hu.omixon.demultiplexer.sequence.SequenceSample;
import hu.omixon.demultiplexer.util.ResourceUtil;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IOServiceReadSequenceTest {

    private final IOService ioService = new IOService();

    private static final String RESOURCE_DIR = "read_sequence";

    @Test
    void testReadSequences_NotExistingFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/no-such-file.seq");

        assertThrows(FileNotFoundException.class, () -> ioService.readSequences(absolutePath));
    }

    @Test
    void testReadSequences_EmptyFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/empty.seq");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ioService.readSequences(absolutePath));
        assertEquals("Input file " + absolutePath + " does not contain any valid sequences", exception.getMessage());

    }

    @Test
    void testReadSequences_NormalFile() throws IOException {

        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/normal_sequence.seq");

        SequenceSample result = ioService.readSequences(absolutePath);

        assertEquals(4, result.sequences().size());
        assertEquals(30, result.sequences().getFirst().nucleotideBaseChain().size());

    }

    @Test
    void testReadSequences_FileWithEmptyLines() throws IOException {

        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/sequence_emptylines.seq");

        SequenceSample result = ioService.readSequences(absolutePath);

        assertEquals(4, result.sequences().size());
        assertEquals(30, result.sequences().getFirst().nucleotideBaseChain().size());

    }

    @Test
    void testReadSequences_InvalidFile() {
        String absolutePath = ResourceUtil.getAsAbsolutePath(RESOURCE_DIR + "/invalid_sequence.seq");

        assertThrows(IllegalArgumentException.class, () -> ioService.readSequences(absolutePath));
    }

}