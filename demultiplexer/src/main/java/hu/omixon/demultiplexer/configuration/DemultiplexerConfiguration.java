package hu.omixon.demultiplexer.configuration;

import java.util.List;

public record DemultiplexerConfiguration(List<ConfigSection> sections) {

    public DemultiplexerConfiguration {
        if (sections == null || sections.isEmpty()) {
            throw new IllegalArgumentException("Configuration sections cannot be empty");
        }
    }

    public ConfigSection findSectionByAllignment(Allignment allignment) {

        if (allignment == null) {
            throw new IllegalArgumentException("Allignment cannot be null");
        }

        return this.sections.stream()
                .filter(e -> allignment.equals(e.getAllignment()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No " + allignment + "section was found in configuration"));

    }

}
