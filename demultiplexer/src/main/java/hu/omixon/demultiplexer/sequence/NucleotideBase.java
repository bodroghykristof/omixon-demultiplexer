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

    public static NucleotideBase findByShortName(String shortName) {
        for (NucleotideBase base : values()) {
            if (base.getShortName().equalsIgnoreCase(shortName)) {
                return base;
            }
        }
        throw new IllegalArgumentException("No NucleotideBase found with short name: " + shortName);
    }

}
