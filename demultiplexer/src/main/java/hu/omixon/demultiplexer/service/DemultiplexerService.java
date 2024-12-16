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

    private final DataConversionService dataConversionService;

    public void run(String sequenceSampleFilePath, String configFilePath,
                    String outputFilePrefix, Allignment allignment) {

        try {
            SequenceSample sequenceSample = dataConversionService.readSequences(sequenceSampleFilePath);
            DemultiplexerConfiguration configuration = dataConversionService.readConfiguration(configFilePath);
            ConfigSection configSection = configuration.findSectionByAllignment(allignment);
            DemultiplexerResult result = configSection.splitSequenceToGroups(sequenceSample);
            dataConversionService.writeResultToFile(result, outputFilePrefix, sequenceSample);
        } catch (Exception e) {
            log.error("An error occurred while performing operation.", e);
        }

    }

}
