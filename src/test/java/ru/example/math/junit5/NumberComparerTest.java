package ru.example.math.junit5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.example.math.NumberComparer;

public class NumberComparerTest {

    @Test
    void testEquality() {
        assertTrue(NumberComparer.areEqual(5, 5));
        assertFalse(NumberComparer.areEqual(5, 6));
    }

    @Test
    void testComparison() {
        assertTrue(NumberComparer.isFirstGreater(10, 5));
        assertFalse(NumberComparer.isFirstGreater(5, 10));
        assertFalse(NumberComparer.isFirstGreater(5, 5));
    }
}