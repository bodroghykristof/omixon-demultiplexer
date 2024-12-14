package hu.omixon.demultiplexer.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AllignmentTest {

    @Test
    void testFindByNameValidName() {
        String name = "bestAlignment";

        Allignment alignment = Allignment.findByName(name);

        assertEquals(Allignment.BEST, alignment, "Expected BEST alignment for name 'bestAlignment'");
    }

    @Test
    void testFindByNameInvalidName() {
        String name = "unknownAlignment";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Allignment.findByName(name);
        });
        assertEquals("No Allignment found with name: unknownAlignment", exception.getMessage());
    }

    @Test
    void testFindByNameCaseInsensitive() {
        String name = "EndsAlignment";

        Allignment alignment = Allignment.findByName(name);

        assertEquals(Allignment.ENDS, alignment, "Expected ENDS alignment for name 'EndsAlignment'");
    }

}