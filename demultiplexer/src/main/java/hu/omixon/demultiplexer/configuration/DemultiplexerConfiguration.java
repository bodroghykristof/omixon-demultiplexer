package hu.omixon.demultiplexer.configuration;

import java.util.ArrayList;
import java.util.List;

public class DemultiplexerConfiguration {

    private List<ConfigSection> sections;

    public void addSection(ConfigSection section) {

        if (this.sections == null) {
            this.sections = new ArrayList<>();
        }

        this.sections.add(section);

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
