package hu.omixon.demultiplexer.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor @Getter
public class ConfigSection {

    private final Allignment allignment;
    private List<ConfigGroupDefinition> groupDefinitions;

}
