public class MainTask2 {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[3];
        shapes[0] = new Circle(5, "Красный", "Синий");
        shapes[1] = new Rectangle(4, 6, "Зеленый", "Желтый");
        shapes[2] = new Triangle(3, "Синий", "Красный");

        for (Shape shape : shapes) {
            System.out.println("Фигура: " + shape.getClass().getSimpleName());
            System.out.printf("Периметр: %.2f%n", shape.getPerimeter());
            System.out.printf("Площадь: %.2f%n", shape.getArea());
            System.out.println("Цвет заливки: " + shape.getFillColor());
            System.out.println("Цвет границы: " + shape.getBorderColor());
        }
    }
}
