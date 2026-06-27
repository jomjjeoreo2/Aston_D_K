package ru.example.math.testng;

import org.testng.annotations.Test;
import ru.example.math.NumberComparer;
import static org.testng.Assert.*;

public class NumberComparerTest {

    @Test
    public void testEquality() {
        assertTrue(NumberComparer.areEqual(5, 5));
        assertFalse(NumberComparer.areEqual(5, 6));
    }

    @Test
    public void testComparison() {
        assertTrue(NumberComparer.isFirstGreater(10, 5));
        assertFalse(NumberComparer.isFirstGreater(5, 10));
    }
}