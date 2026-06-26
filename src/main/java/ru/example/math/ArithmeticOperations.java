package ru.example.math;

public class ArithmeticOperations {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static double divide(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Деление на ноль!");
        }
        return (double) dividend / divisor;
    }
}