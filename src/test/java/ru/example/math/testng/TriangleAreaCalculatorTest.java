package ru.example.math.testng;

import org.testng.annotations.Test;
import ru.example.math.TriangleAreaCalculator;
import static org.testng.Assert.*;

public class TriangleAreaCalculatorTest {

    @Test
    public void testEquilateralTriangle() {
        assertEquals(TriangleAreaCalculator.area(1, 1, 1), Math.sqrt(3) / 4, 0.0001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testZeroSide() {
        TriangleAreaCalculator.area(0, 1, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testImpossibleTriangle() {
        TriangleAreaCalculator.area(1, 1, 3);
    }
}