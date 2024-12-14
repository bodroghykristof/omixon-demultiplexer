package hu.omixon.demultiplexer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.Sequence;
import hu.omixon.demultiplexer.sequence.SequenceSample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class IOService {

    public SequenceSample readSequences(String sequenceSampleFilePath) throws IOException {

        List<Sequence> sequences;

        try (BufferedReader reader = new BufferedReader(new FileReader(sequenceSampleFilePath))) {
            sequences = reader.lines()
                            .filter(line -> !line.isEmpty())
                            .map(Sequence::fromBaseChain)
                            .toList();
        }

        if (sequences.isEmpty()) {
            throw new IllegalArgumentException("Input file " + sequenceSampleFilePath + " does not contain any valid sequences");
        }

        return new SequenceSample(sequences);
    }

    public DemultiplexerConfiguration readConfiguration(String configFilePath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode configJson = objectMapper.readTree(new File(configFilePath));
        // TODO read sequence data
        return null;
    }

    public void writeResultToFile(DemultiplexerResult result, String outputFilePrefix) {
        // TODO
    }

}
