package ru.example.math;

public class TriangleAreaCalculator {
    public static double area(double a, double b, double c) {
        validateSides(a, b, c);

        double semiPerimeter = (a + b + c) / 2.0;
        return Math.sqrt(
                semiPerimeter * (semiPerimeter - a) * (semiPerimeter - b) * (semiPerimeter - c)
        );
    }

    private static void validateSides(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Все стороны должны быть положительны.");
        }
        if (!(a + b > c && a + c > b && b + c > a)) {
            throw new IllegalArgumentException("Такие стороны не могут образовать треугольник.");
        }
    }
}