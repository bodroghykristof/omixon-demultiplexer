package hu.omixon.demultiplexer.service;

import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.configuration.ConfigSection;
import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DemultiplexerService {

    private final IOService ioService;

    public void run(String sequenceSampleFilePath, String configFilePath, Allignment allignment) {
        // TODO run operation
        System.out.println("Omixon Demultiplexer");

        SequenceSample sequenceSample = ioService.readSequences(sequenceSampleFilePath);
        DemultiplexerConfiguration configuration = ioService.readConfiguration(configFilePath);
        ConfigSection configSection = configuration.findSectionByAllignment(allignment);

    }

}
