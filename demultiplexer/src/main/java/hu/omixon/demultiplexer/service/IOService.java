package hu.omixon.demultiplexer.service;

import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.Sequence;
import hu.omixon.demultiplexer.sequence.SequenceSample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOService {

    public SequenceSample readSequences(String sequenceSampleFilePath) throws IOException {

        List<Sequence> sequences = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(sequenceSampleFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {

                if (!line.isEmpty()) {
                    sequences.add(Sequence.fromBaseChain(line));
                }

            }
        }

        if (sequences.isEmpty()) {
            throw new IllegalArgumentException("Input file " + sequenceSampleFilePath + " does not contain any valid sequences");
        }

        return new SequenceSample(sequences);
    }

    public DemultiplexerConfiguration readConfiguration(String configFilePath) {
        // TODO read sequence data
        return null;
    }

    public void writeResultToFile(DemultiplexerResult result, String outputFilePrefix) {
        // TODO
    }

}
