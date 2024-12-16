package hu.omixon.demultiplexer.service;

import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WriteResultIntegrationTest {

    @BeforeEach
    void setUp() {
        ioService = mock(IOService.class);
        dataConversionService = new DataConversionService(ioService);

        sequenceOne = Sequence.fromBaseChain("ATC");
        sequenceTwo = Sequence.fromBaseChain("TTG");
        sequenceThree = Sequence.fromBaseChain("CGC");
        sequenceFour = Sequence.fromBaseChain("ATT");
    }

    private static IOService ioService;
    private static DataConversionService dataConversionService;

    private static Sequence sequenceOne;
    private static Sequence sequenceTwo;
    private static Sequence sequenceThree;
    private static Sequence sequenceFour;

    @Test
    void testWriteResultToFile_WithoutUnmatchedSequences() throws IOException {

        DemultiplexerResult result = new DemultiplexerResult();

        String outputPrefix = "/path/to/output/20241216_";

        String groupNameOne = "group1";
        String groupNameTwo = "group2";

        result.addResult(groupNameOne, sequenceOne);
        result.addResult(groupNameOne, sequenceTwo);

        result.addResult(groupNameTwo, sequenceOne);
        result.addResult(groupNameTwo, sequenceTwo);
        result.addResult(groupNameTwo, sequenceThree);
        result.addResult(groupNameTwo, sequenceFour);

        ArgumentCaptor<String> fileNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentCaptor = ArgumentCaptor.forClass(String.class);

        dataConversionService.writeResultToFile(result, outputPrefix);

        verify(ioService, times(2)).writeFile(fileNameCaptor.capture(), contentCaptor.capture());

        List<String> fileNames = fileNameCaptor.getAllValues();
        List<String> fileContents = contentCaptor.getAllValues();

        assertEquals("/path/to/output/20241216_group1.seq", fileNames.getFirst());
        assertEquals("/path/to/output/20241216_group2.seq", fileNames.get(1));

        assertEquals("ATC TTG", fileContents.getFirst());
        assertEquals("ATC TTG CGC ATT", fileContents.get(1));

    }

    @Test
    void testWriteResultToFile_WithUnmatchedSequences() throws IOException {

        DemultiplexerResult result = new DemultiplexerResult();

        String outputPrefix = "/path/to/output/20241216_";

        String groupNameOne = "group1";
        String groupNameTwo = "group2";

        result.addResult(groupNameOne, sequenceOne);
        result.addResult(groupNameOne, sequenceTwo);

        result.addResult(groupNameTwo, sequenceOne);
        result.addResult(groupNameTwo, sequenceTwo);
        result.addResult(groupNameTwo, sequenceThree);

        result.collectUnmatchedSequences(List.of(sequenceOne, sequenceTwo, sequenceThree, sequenceFour));

        ArgumentCaptor<String> fileNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentCaptor = ArgumentCaptor.forClass(String.class);

        dataConversionService.writeResultToFile(result, outputPrefix);

        verify(ioService, times(3)).writeFile(fileNameCaptor.capture(), contentCaptor.capture());

        List<String> fileNames = fileNameCaptor.getAllValues();
        List<String> fileContents = contentCaptor.getAllValues();

        assertEquals("/path/to/output/20241216_group1.seq", fileNames.getFirst());
        assertEquals("/path/to/output/20241216_group2.seq", fileNames.get(1));
        assertEquals("/path/to/output/20241216_unmatched.seq", fileNames.get(2));

        assertEquals("ATC TTG", fileContents.getFirst());
        assertEquals("ATC TTG CGC", fileContents.get(1));
        assertEquals("ATT", fileContents.get(2));

    }

}