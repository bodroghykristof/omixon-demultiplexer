package hu.omixon.demultiplexer.configuration.result;

import hu.omixon.demultiplexer.sequence.Sequence;

import java.util.List;

public record DemultiplexerResultGroup(String groupName, List<Sequence> sequences) {
}
