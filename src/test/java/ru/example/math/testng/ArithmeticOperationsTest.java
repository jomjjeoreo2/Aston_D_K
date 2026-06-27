package ru.example.math.testng;

import org.testng.annotations.Test;
import ru.example.math.ArithmeticOperations;
import static org.testng.Assert.*;

public class ArithmeticOperationsTest {

    @Test
    public void testSimpleCalculations() {
        assertEquals(ArithmeticOperations.add(2, 3), 5);
        assertEquals(ArithmeticOperations.multiply(10, 0), 0);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivisionByZero() {
        ArithmeticOperations.divide(10, 0);
    }
}