package hu.omixon.demultiplexer.configuration;

import hu.omixon.demultiplexer.configuration.rule.ConfigRule;
import hu.omixon.demultiplexer.sequence.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigGroupDefinitionTest {

    @Test
    void testConstructor_WithNullRule() {
        String groupName = "TestGroup";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ConfigGroupDefinition(groupName, null);
        });
        assertEquals("Config rule must not be null", exception.getMessage());
    }

    @Test
    void testGetMatchValue_DelegatesToConfigRule() {
        String groupName = "TestGroup";
        ConfigRule mockConfigRule = mock(ConfigRule.class);
        Sequence mockSequence = mock(Sequence.class);
        int expectedMatchValue = 5;

        when(mockConfigRule.getMatchValue(mockSequence)).thenReturn(expectedMatchValue);

        ConfigGroupDefinition configGroupDefinition = new ConfigGroupDefinition(groupName, mockConfigRule);

        int actualMatchValue = configGroupDefinition.getMatchValue(mockSequence);

        assertEquals(expectedMatchValue, actualMatchValue);
        verify(mockConfigRule, times(1)).getMatchValue(mockSequence);
    }

    @Test
    void testIsMatch_DelegatesToConfigRule() {
        String groupName = "TestGroup";
        ConfigRule mockConfigRule = mock(ConfigRule.class);
        Sequence mockSequence = mock(Sequence.class);

        when(mockConfigRule.getMatchValue(mockSequence)).thenReturn(1);

        ConfigGroupDefinition configGroupDefinition = new ConfigGroupDefinition(groupName, mockConfigRule);

        boolean isMatch = configGroupDefinition.isMatch(mockSequence);

        assertTrue(isMatch);
        verify(mockConfigRule, times(1)).getMatchValue(mockSequence);

        when(mockConfigRule.getMatchValue(mockSequence)).thenReturn(0);
        isMatch = configGroupDefinition.isMatch(mockSequence);
        assertFalse(isMatch);
        verify(mockConfigRule, times(2)).getMatchValue(mockSequence);
    }

}