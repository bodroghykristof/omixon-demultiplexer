package hu.omixon.demultiplexer.service;

import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.SequenceSample;

public class IOService {

    public SequenceSample readSequences(String sequenceSampleFilePath) {
        // TODO read sequence data
        return null;
    }

    public DemultiplexerConfiguration readConfiguration(String configFilePath) {
        // TODO read sequence data
        return null;
    }

    public void writeResultToFile(DemultiplexerResult result, String outputFilePrefix) {
        // TODO
    }

}
