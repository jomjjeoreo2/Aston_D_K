package ru.example.math.junit5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.example.math.FactorialCalculator;

public class FactorialCalculatorTest {

    @Test
    void testBasicFactorials() {
        assertEquals(1, FactorialCalculator.factorial(0));
        assertEquals(1, FactorialCalculator.factorial(1));
        assertEquals(120, FactorialCalculator.factorial(5));
    }

    @Test
    void testNegativeInput() {
        // проверка выброса исключения
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> FactorialCalculator.factorial(-1)
        );
        assertTrue(exception.getMessage().contains("отрицательные"));
    }
}