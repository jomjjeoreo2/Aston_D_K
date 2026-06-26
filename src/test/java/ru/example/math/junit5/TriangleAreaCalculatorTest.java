package ru.example.math.junit5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.example.math.TriangleAreaCalculator;

public class TriangleAreaCalculatorTest {

    @Test
    void testValidTriangles() {
        // равносторонний
        assertEquals(Math.sqrt(3)/4, TriangleAreaCalculator.area(1, 1, 1), 0.0001);
        // прямоугольный
        assertEquals(6.0, TriangleAreaCalculator.area(3, 4, 5));
    }

    @Test
    void testInvalidInputs() {
        // сторона ≤ 0
        assertThrows(IllegalArgumentException.class, () -> TriangleAreaCalculator.area(3, 4, -5));
        // невозможный треугольник
        assertThrows(IllegalArgumentException.class, () -> TriangleAreaCalculator.area(1, 1, 3));
    }
}
