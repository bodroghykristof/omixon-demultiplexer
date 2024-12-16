package hu.omixon.demultiplexer.configuration.result;

import hu.omixon.demultiplexer.sequence.Sequence;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @ToString
public final class DemultiplexerResult {

    private List<DemultiplexerResultGroup> groups;

    public void addResult(String groupName, Sequence sequence) {

        if (this.groups == null) {
            this.groups = new ArrayList<>();
        }

        Optional<DemultiplexerResultGroup> existingGroup = groups.stream()
                                                                .filter(e -> groupName.equals(e.getGroupName()))
                                                                .findFirst();

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

}
