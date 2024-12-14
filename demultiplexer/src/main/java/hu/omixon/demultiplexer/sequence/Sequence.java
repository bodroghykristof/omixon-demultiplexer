package hu.omixon.demultiplexer.sequence;

import java.util.List;

public record Sequence(List<NucleotideBase> nucleotideBaseChain) {

    public Sequence {
        if (nucleotideBaseChain == null || nucleotideBaseChain.isEmpty()) {
            throw new IllegalArgumentException("nucleotideBaseChain cannot be null or empty");
        }
    }

    public static Sequence fromBaseChain(String baseChain) {

        if (baseChain == null || baseChain.isEmpty()) {
            throw new IllegalArgumentException("Sequence cannot be initialized with empty baseChain");
        }

        List<NucleotideBase> baseList = baseChain.chars()
                                                .mapToObj(c -> Character.toString((char) c)) // Convert int to char
                                                .map(NucleotideBase::findByShortName)
                                                .toList();
        return new Sequence(baseList);

    }

}
