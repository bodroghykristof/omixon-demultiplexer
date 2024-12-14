package hu.omixon.demultiplexer.service;

import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.configuration.ConfigSection;
import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor @Slf4j
public class DemultiplexerService {

    private final IOService ioService;

    public void run(String sequenceSampleFilePath, String configFilePath,
                    Allignment allignment, String outputFilePrefix) {

        try {
            SequenceSample sequenceSample = ioService.readSequences(sequenceSampleFilePath);
            DemultiplexerConfiguration configuration = ioService.readConfiguration(configFilePath);
            ConfigSection configSection = configuration.findSectionByAllignment(allignment);
            DemultiplexerResult result = configSection.splitSequenceToGroups(sequenceSample);
            ioService.writeResultToFile(result, outputFilePrefix);
        } catch (Exception e) {
            log.error("An error occurred while performing operation.", e);
        }

    }

}
