package hu.omixon.demultiplexer.configuration.result;

import hu.omixon.demultiplexer.sequence.Sequence;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@Getter @ToString
public final class DemultiplexerResult {

    private List<DemultiplexerResultGroup> groups;
    private List<Sequence> unmatchedSequences;

    public void addResult(String groupName, Sequence sequence) {

        if (sequence == null) {
            return;
        }

        if (this.groups == null) {
            this.groups = new ArrayList<>();
        }

        Optional<DemultiplexerResultGroup> existingGroup = findGroupByName(groupName);

        if (existingGroup.isPresent()) {
            existingGroup.get().addSequence(sequence);
        } else {
            DemultiplexerResultGroup group = new DemultiplexerResultGroup(groupName);
            group.addSequence(sequence);
            this.groups.add(group);
        }

    }

    public int countGroups() {
        return this.groups == null ? 0 : this.groups.size();
    }

    public Optional<DemultiplexerResultGroup> findGroupByName(String groupName) {

        if (groupName == null) {
            throw new IllegalArgumentException("GroupName cannot ba null");
        }

        if (this.groups == null) {
            return Optional.empty();
        }

        return this.groups.stream()
                        .filter(e -> groupName.equals(e.getGroupName()))
                        .findFirst();

    }

    public void collectUnmatchedSequences(List<Sequence> sequences) {

        if (this.groups == null) {
            return;
        }

        Set<Sequence> matchedSequences = this.groups.stream()
                                                    .flatMap(e -> e.getSequences().stream())
                                                    .collect(Collectors.toSet());

        this.unmatchedSequences = sequences.stream()
                                        .filter(e -> !matchedSequences.contains(e))
                                        .toList();

    }

    public List<Sequence> getUnmatchedSequences() {
        return this.unmatchedSequences == null ? Collections.emptyList() : this.unmatchedSequences;
    }

}
