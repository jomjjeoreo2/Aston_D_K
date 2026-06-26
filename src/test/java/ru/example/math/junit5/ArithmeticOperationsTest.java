package ru.example.math.junit5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.example.math.ArithmeticOperations;

public class ArithmeticOperationsTest {

    @Test
    void testAdditionAndSubtraction() {
        assertEquals(5, ArithmeticOperations.add(2, 3));
        assertEquals(-1, ArithmeticOperations.subtract(2, 3));
    }

    @Test
    void testMultiplication() {
        assertEquals(10, ArithmeticOperations.multiply(2, 5));
        assertEquals(0, ArithmeticOperations.multiply(0, 100));
    }

    @Test
    void testDivision() {
        // обычное деление
        assertEquals(2.0, ArithmeticOperations.divide(10, 5));
        // деление на 0
        assertThrows(ArithmeticException.class, () -> ArithmeticOperations.divide(10, 0));
    }
}
