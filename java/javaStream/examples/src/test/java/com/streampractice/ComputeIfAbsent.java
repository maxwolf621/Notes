package com.streampractice;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ComputeIfAbsent {
    @Test
    public void whenKeyIsPresent_thenFetchTheValue() {
            Map<String, Integer> stringLength = new HashMap<>();
            stringLength.put("John", 5);
            assertEquals((long)stringLength.computeIfAbsent("John", s -> s.length()), 5);
    }

    @Test
    public void whenKeyIsNotPresent_thenComputeTheValueUsingMappingFunctionAndStore() {
            Map<String, Integer> stringLength = new HashMap<>();

            assertEquals((long)stringLength.computeIfAbsent("John", s -> s.length()), 4);
            assertEquals((long)stringLength.get("John"), 4);
    }

    @Test
    public void whenMappingFunctionReturnsNull_thenDoNotRecordMapping() {
            Map<String, Integer> stringLength = new HashMap<>();
            
            // Compute if key : John is absent.
            // Then new key : John value : null
            assertEquals(stringLength.computeIfAbsent("John", s -> null), null);

            assertNull(stringLength.get("John"));
    }

    @Test(expected = RuntimeException.class)
    public void whenMappingFunctionThrowsException_thenExceptionIsRethrown() {
            Map<String, Integer> stringLength = new HashMap<>();
            stringLength.computeIfAbsent("John", s -> {throw new RuntimeException();});
    }
}
