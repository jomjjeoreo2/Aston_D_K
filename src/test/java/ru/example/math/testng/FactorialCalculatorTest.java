package ru.example.math.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.example.math.FactorialCalculator;
import static org.testng.Assert.*;

public class FactorialCalculatorTest {

    @DataProvider(name = "factorialCases")
    public Object[][] provideFactorialCases() {
        return new Object[][]{
                {0, 1},
                {1, 1},
                {5, 120}
        };
    }

    @Test(dataProvider = "factorialCases")
    public void testFactorial(int input, long expected) {
        assertEquals(expected, FactorialCalculator.factorial(input));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNegativeInput() {
        FactorialCalculator.factorial(-1);
    }
}