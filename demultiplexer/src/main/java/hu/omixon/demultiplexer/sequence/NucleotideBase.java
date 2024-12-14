package hu.omixon.demultiplexer.sequence;

import lombok.Getter;

@Getter
public enum NucleotideBase {

    ADENINE("A", "Adenine"),
    CYTOSINE("C", "Cytosine"),
    GUANINE("G", "Guanine"),
    THYMINE("T", "Thymine");

    private final String shortName;
    private final String longName;

    NucleotideBase(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

}
