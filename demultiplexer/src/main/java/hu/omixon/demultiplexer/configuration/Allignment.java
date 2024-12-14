package hu.omixon.demultiplexer.configuration;

import lombok.Getter;

@Getter
public enum Allignment {

    ENDS("endsAlignment"),
    MID("midAlignment"),
    BEST("bestAlignment");

    private final String name;

    Allignment(String name) {
        this.name = name;
    }

    public static Allignment findByName(String name) {
        for (Allignment alignment : values()) {
            if (alignment.getName().equalsIgnoreCase(name)) {
                return alignment;
            }
        }
        throw new IllegalArgumentException("No Allignment found with name: " + name);
    }

}
