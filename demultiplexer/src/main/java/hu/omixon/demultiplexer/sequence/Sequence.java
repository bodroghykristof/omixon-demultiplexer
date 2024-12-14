package hu.omixon.demultiplexer.sequence;

import java.util.List;

public record Sequence(List<NucleotideBase> nucleotideBaseChain) {

    public Sequence {
        if (nucleotideBaseChain == null || nucleotideBaseChain.isEmpty()) {
            throw new IllegalArgumentException("nucleotideBaseChain cannot be null or empty");
        }
    }

}
