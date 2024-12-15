package hu.omixon.demultiplexer.sequence;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SequenceTest {

    @Test
    void testFromBaseChain_WithNull() {
        String baseChain = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> Sequence.fromBaseChain(baseChain));

        assertEquals("Sequence cannot be initialized with empty baseChain", exception.getMessage());
    }

    @Test
    void testFromBaseChain_WithEmptyString() {
        String baseChain = "";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> Sequence.fromBaseChain(baseChain));

        assertEquals("Sequence cannot be initialized with empty baseChain", exception.getMessage());
    }

    @Test
    void testFromBaseChain_WithValidInput() {
        String baseChain = "AGTCC";

        Sequence sequence = Sequence.fromBaseChain(baseChain);

        assertEquals(5, sequence.nucleotideBaseChain().size());
    }

    @Test
    void testFromBaseChain_CaseInsensitive() {
        String baseChain = "agtcc";

        Sequence sequence = Sequence.fromBaseChain(baseChain);

        assertEquals(5, sequence.nucleotideBaseChain().size());
    }

    @Test
    void testFromBaseChain_WithInvalidInput() {
        String baseChain = "AGCXT";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> Sequence.fromBaseChain(baseChain));

        assertEquals("No NucleotideBase found with short name: X", exception.getMessage());
    }

    @Test
    void testToBaseChainString() {
        List<NucleotideBase> baseList = List.of(NucleotideBase.ADENINE, NucleotideBase.GUANINE, 
                NucleotideBase.CYTOSINE, NucleotideBase.THYMINE);
        
        Sequence sequence = new Sequence(baseList);
        
        assertEquals("AGCT", sequence.toBaseChainString());

    }


}