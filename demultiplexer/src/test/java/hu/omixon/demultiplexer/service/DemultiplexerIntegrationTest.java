package hu.omixon.demultiplexer.service;

import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.util.ResourceUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DemultiplexerIntegrationTest {

    @BeforeAll
    static void beforeAll() {
        IOService ioService = new IOService();
        DataConversionService dataConversionService = new DataConversionService(ioService);
        demultiplexerService = new DemultiplexerService(dataConversionService);
    }

    private static final String RESOURCE_DIR = "demultiplex/";

    private static DemultiplexerService demultiplexerService;

    @ParameterizedTest
    @MethodSource("getExampleNames")
    void testDemultiplexProcess(Path exampleName) throws IOException {

        String sequenceFile = exampleName + "/sequence.seq";
        String config = exampleName + "/config.conf";

        for (Allignment allignment : Allignment.values()) {
            String testCaseDirectory = exampleName + "/" + allignment.name().toLowerCase();
            String outputPrefix = testCaseDirectory + "/out_";
            demultiplexerService.run(sequenceFile, config, allignment, outputPrefix);
            compareSequences(testCaseDirectory);
        }

    }

    private static Stream<Path> getExampleNames() throws IOException {
        return ResourceUtil.listDirectories(RESOURCE_DIR).stream();
    }

    private void compareSequences(String testCaseDirectory) throws IOException {

        List<File> files = ResourceUtil.listFiles(testCaseDirectory);
        assertFalse(files.isEmpty());

        List<File> resultFiles = files.stream().filter(f -> f.getName().startsWith("out_")).toList();
        List<File> expectedResultFiles = files.stream().filter(f -> !f.getName().startsWith("out_")).toList();

        assertEquals(expectedResultFiles.size(), resultFiles.size());

        for (File result : resultFiles) {

            Optional<File> expectedResult = expectedResultFiles.stream()
                                                            .filter(f -> ("out_" + f.getName()).equals(result.getName()))
                                                            .findFirst();
            assertTrue(expectedResult.isPresent());

            Set<String> expectedSequences = getSequences(expectedResult.get());
            Set<String> resultSequences = getSequences(result);

            assertEquals(expectedSequences, resultSequences);

            result.deleteOnExit();

        }

    }

    private static Set<String> getSequences(File result) throws IOException {
        return Arrays.stream(ResourceUtil.readFileContent(result)
                .split("\\s+"))
                .collect(Collectors.toSet());
    }


}
