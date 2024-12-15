package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
public record BestRule(Sequence infix) implements ConfigRule {

    public BestRule {
        if (infix == null) {
            throw new IllegalArgumentException("Infix must be defined");
        }
    }

    @Override
    public int getMatchValue(Sequence sequence) {

        if (sequence == null) {
            throw new IllegalArgumentException("Cannot run rule on null sequence");
        }

        int sequenceLength = sequence.nucleotideBaseChain().size();
        int infixLength = this.infix.nucleotideBaseChain().size();

        if (sequenceLength < infixLength) {
            log.warn("Provided sequence is shorter that infix of rule. Match value is implicitly 0.");
            return 0;
        }

        String infixAsString = this.infix.toBaseChainString();
        String sequenceAsString = sequence.toBaseChainString();

        return IntStream.rangeClosed(0, sequenceLength - infixLength)
                        .map(i -> countCommonCharacters(sequenceAsString.substring(i, i + infixLength), infixAsString))
                        .sum();
    }

    private int countCommonCharacters(String strOne, String strTwo) {
        // same length of strOne and strTwo is asserted
        return (int) IntStream.range(0, strOne.length())
                            .filter(i -> strOne.charAt(i) == strTwo.charAt(i))
                            .count();
    }

}
