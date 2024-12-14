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

}
