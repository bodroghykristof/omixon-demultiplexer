package hu.omixon.demultiplexer.configuration.rule;

import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BestRuleTest {

    @Test
    void testConstructor_WithEmptyInfix() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new BestRule(null));

        assertEquals("Infix must be defined", exception.getMessage());
    }

    @Test
    void testGetMatchValue_WithTooShortSequence() {
        Sequence sequence = Sequence.fromBaseChain("ACTG");
        Sequence infix = Sequence.fromBaseChain("GGTCACACTT");

        BestRule bestRule = new BestRule(infix);

        assertEquals(0, bestRule.getMatchValue(sequence));
    }

    @ParameterizedTest
    @MethodSource("getArgsForPositiveMatches")
    void testGetMatchValue_WithMatch(String sequenceString, String infixString, int expectedMatch) {
        Sequence sequence = Sequence.fromBaseChain(sequenceString);
        Sequence infix = Sequence.fromBaseChain(infixString);

        BestRule bestRule = new BestRule(infix);

        assertEquals(expectedMatch, bestRule.getMatchValue(sequence));
    }

    @ParameterizedTest
    @MethodSource("getArgsForNegativeMatches")
    void testGetMatchValue_WithNoMatch(String sequenceString, String infixString) {
        Sequence sequence = Sequence.fromBaseChain(sequenceString);
        Sequence infix = Sequence.fromBaseChain(infixString);

        BestRule bestRule = new BestRule(infix);

        assertEquals(0, bestRule.getMatchValue(sequence));
    }

    private static Stream<Arguments> getArgsForPositiveMatches() {
        return Stream.of(
                Arguments.of("ATCATG", "ATG", 3),
                Arguments.of("ATTTTA", "TT", 2),
                Arguments.of("AGCGTA", "G", 1),
                Arguments.of("AGCGTA", "AGCGT", 5),
                Arguments.of("AGCGTA", "GCGTA", 5),
                Arguments.of("AGCGTA", "GCGT", 4),
                Arguments.of("AGCGTA", "AGCGTA", 6)
        );
    }

    private static Stream<Arguments> getArgsForNegativeMatches() {
        return Stream.of(
                Arguments.of("GCTGC", "AA"),
                Arguments.of("GCTGC", "A"),
                Arguments.of("GCGGA", "ATT"),
                Arguments.of("AGGCG", "TAT")
        );
    }


}