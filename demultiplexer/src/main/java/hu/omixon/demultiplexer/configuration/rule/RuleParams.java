package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;

public record RuleParams(Sequence prefix, Sequence postfix, Sequence infix) {
}
