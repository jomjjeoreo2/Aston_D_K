public class Triangle implements Shape {
    private final double sideLength;
    private final String fillColor;
    private final String borderColor;

    public Triangle(double sideLength, String fillColor, String borderColor) {
        this.sideLength = sideLength;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double getArea() {
        return (Math.sqrt(3) / 4) * sideLength * sideLength;
    }

    @Override
    public double getPerimeter() {
        return sideLength * 3;
    }

    @Override
    public String getFillColor() {
        return fillColor;
    }

    @Override
    public String getBorderColor() {
        return borderColor;
    }
}
