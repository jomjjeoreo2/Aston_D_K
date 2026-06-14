public class Cat extends Animal {
    private static int totalCats = 0;
    private boolean isFull = false;
    private static final int MAX_RUN_DISTANCE = 200;
    private String name;

    public Cat(String name) {
        super(name);
        this.name = name;
        totalCats++;
    }

    public static int getTotalCats() {
        return totalCats;
    }

    public String getName() {
        return name;
    }

    public void run(int distance) {
        if (distance <= 0) {
            System.out.println("Дистанция должна быть положительной");
            return;
        }
        if (distance <= MAX_RUN_DISTANCE) {
            System.out.println(name + " пробежал " + distance + "м.");
        } else {
            System.out.println(name + " не может пробежать " + distance + "м. Максимум для кота - " + MAX_RUN_DISTANCE + "м.");
        }
    }

    public void swim(int distance) {
        System.out.println(name + " не умеет плавать");
    }

    public void eat(int appetite) {
        if (appetite <= 0) {
            System.out.println("Аппетит должен быть положительным");
            return;
        }
        isFull = true;
        System.out.println(name + " поел и теперь сытый");
    }

    public boolean isFull() {
        return isFull;
    }
}
