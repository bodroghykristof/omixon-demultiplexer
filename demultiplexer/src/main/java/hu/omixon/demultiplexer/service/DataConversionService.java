package hu.omixon.demultiplexer.service;

import com.fasterxml.jackson.databind.JsonNode;
import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.configuration.ConfigGroupDefinition;
import hu.omixon.demultiplexer.configuration.ConfigSection;
import hu.omixon.demultiplexer.configuration.DemultiplexerConfiguration;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResult;
import hu.omixon.demultiplexer.configuration.result.DemultiplexerResultGroup;
import hu.omixon.demultiplexer.configuration.rule.ConfigRule;
import hu.omixon.demultiplexer.configuration.rule.ConfigRuleFactory;
import hu.omixon.demultiplexer.configuration.rule.RuleParams;
import hu.omixon.demultiplexer.sequence.Sequence;
import hu.omixon.demultiplexer.sequence.SequenceSample;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j @AllArgsConstructor
public class DataConversionService {

    private final IOService ioService;

    public SequenceSample readSequences(String sequenceSampleFilePath) throws IOException {

        List<Sequence> sequences = ioService.readFileLineByLine(sequenceSampleFilePath, Sequence::fromBaseChain);

        if (sequences.isEmpty()) {
            throw new IllegalArgumentException("Input file " + sequenceSampleFilePath + " does not contain any valid sequences");
        }

        return new SequenceSample(sequences);
    }

    public DemultiplexerConfiguration readConfiguration(String configFilePath) throws IOException {

        JsonNode configJson = ioService.readJsonFromFile(configFilePath);

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
        ConfigRule configRule = ConfigRuleFactory.createConfigRule(ruleParams, allignment);

        return new ConfigGroupDefinition(field.getKey(), configRule);
    }

    private Sequence getSequenceForRule(JsonNode groupRules, String rulePart) {
        JsonNode value = groupRules.get(rulePart);
        if (value != null && value.isTextual()) {
            return Sequence.fromBaseChain(value.asText());
        }
        return null;
    }

    public void writeResultToFile(DemultiplexerResult result, String outputFilePrefix) throws IOException {
        writeGroups(result, outputFilePrefix);
        writeUnmatchedSequences(result, outputFilePrefix);
    }

    private void writeGroups(DemultiplexerResult result, String outputFilePrefix) throws IOException {

        for (DemultiplexerResultGroup resultGroup : result.getGroups()) {

            if (!resultGroup.getSequences().isEmpty()) {

                String filename = outputFilePrefix + resultGroup.getGroupName() + ".seq";
                String content = joinSequencesForOutput(resultGroup.getSequences());

                ioService.writeFile(filename, content);

            }
        }
    }

    private void writeUnmatchedSequences(DemultiplexerResult result, String outputFilePrefix) throws IOException {

        if (!result.getUnmatchedSequences().isEmpty()) {

            String filename = outputFilePrefix + "unmatched.seq";
            String content = joinSequencesForOutput(result.getUnmatchedSequences());

            ioService.writeFile(filename, content);

        }
    }

    private String joinSequencesForOutput(List<Sequence> sequences) {
        return sequences.stream()
                        .map(Sequence::toBaseChainString)
                        .collect(Collectors.joining(" "));
    }

}
