package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;
import lombok.extern.slf4j.Slf4j;

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

        if (sequence.nucleotideBaseChain().size() < this.infix.nucleotideBaseChain().size()) {
            log.warn("Provided sequence is shorter that infix of rule. Match value is implicitly 0.");
            return 0;
        }

        String infixAsString = this.infix.toBaseChainString();
        String sequenceAsString = sequence.toBaseChainString();

        return 1;
    }

}
