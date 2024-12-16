package hu.omixon.demultiplexer.service;

import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.configuration.ConfigSection;
import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@AllArgsConstructor @Slf4j
public class DemultiplexerService {

    private final DataConversionService dataConversionService;

    public void run(String sequenceSampleFilePath, String configFilePath,
                    String outputFilePrefix, Allignment allignment) throws IOException {

            SequenceSample sequenceSample = dataConversionService.readSequences(sequenceSampleFilePath);
            DemultiplexerConfiguration configuration = dataConversionService.readConfiguration(configFilePath);
            ConfigSection configSection = configuration.findSectionByAllignment(allignment);
            DemultiplexerResult result = configSection.splitSequenceToGroups(sequenceSample);
            dataConversionService.writeResultToFile(result, outputFilePrefix, sequenceSample);

    }

}
