package hu.omixon.demultiplexer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.configuration.ConfigGroupDefinition;
import hu.omixon.demultiplexer.configuration.ConfigSection;
import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.configuration.rule.RuleParams;
import hu.omixon.demultiplexer.sequence.Sequence;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
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

        List<ConfigSection> sections = Arrays.stream(Allignment.values())
                                            .map(e -> readConfigSection(configJson, e))
                                            .filter(Objects::nonNull)
                                            .toList();

        return new DemultiplexerConfiguration(sections);
    }

    private ConfigSection readConfigSection(JsonNode config, Allignment allignment) {

        ConfigSection configSection = new ConfigSection(allignment);

        JsonNode allignmentNode = config.get(allignment.getName());
        if (allignmentNode == null || allignmentNode.isEmpty()) {
            log.warn("No {} part was found in config. Skipping.", allignment.getName());
            return null;
        }

        Iterator<Map.Entry<String, JsonNode>> groups = allignmentNode.fields();
        while (groups.hasNext()) {
            ConfigGroupDefinition groupDefinition = getConfigGroupDefinition(allignment, groups);
            configSection.addGroupDefinition(groupDefinition);
        }

        return configSection;

    }

    private ConfigGroupDefinition getConfigGroupDefinition(Allignment allignment, Iterator<Map.Entry<String, JsonNode>> groups) {
        Map.Entry<String, JsonNode> field = groups.next();

        Sequence prefixSequence = getSequenceForRule(field.getValue(), "prefix");
        Sequence postfixSequence = getSequenceForRule(field.getValue(), "postfix");
        Sequence infixSequence = getSequenceForRule(field.getValue(), "infix");

        RuleParams ruleParams = new RuleParams(prefixSequence, postfixSequence, infixSequence);

        return new ConfigGroupDefinition(field.getKey(), allignment, ruleParams);
    }

    private Sequence getSequenceForRule(JsonNode groupRules, String rulePart) {
        JsonNode value = groupRules.get(rulePart);
        if (value != null && value.isTextual()) {
            return Sequence.fromBaseChain(value.asText());
        }
        return null;
    }

    public void writeResultToFile(DemultiplexerResult result, String outputFilePrefix) {
        // TODO
    }

}
