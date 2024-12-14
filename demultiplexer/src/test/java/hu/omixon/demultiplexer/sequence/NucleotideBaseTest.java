package hu.omixon.demultiplexer.sequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NucleotideBaseTest {

    @Test
    void testFindByShortName_ValidShortName() {
        NucleotideBase result = NucleotideBase.findByShortName("A");
        assertNotNull(result);
        assertEquals(NucleotideBase.ADENINE, result);
    }

    @Test
    void testFindByShortName_InvalidShortName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> NucleotideBase.findByShortName("X"));
        assertEquals("No NucleotideBase found with short name: X", exception.getMessage());
    }

    @Test
    void testFindByShortName_CaseInsensitiveMatch() {
        NucleotideBase result = NucleotideBase.findByShortName("c");
        assertNotNull(result);
        assertEquals(NucleotideBase.CYTOSINE, result);
    }

}