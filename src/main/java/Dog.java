public class Dog extends Animal {
    private static int totalDogs = 0;
    private static final int MAX_RUN_DISTANCE = 500;
    private static final int MAX_SWIM_DISTANCE = 10;

    public Dog(String name) {
        super(name);
        totalDogs++;
    }
    public static int getTotalDogs() {
        return totalDogs;
    }

    public void run(int distance) {
        if (distance <= 0) {
            System.out.println("Дистанция должна быть положительной");
            return;
        }
        if (distance <= MAX_RUN_DISTANCE) {
            System.out.println(name + " пробежал " + distance + "м.");
        } else {
            System.out.println(name + " не может пробежать " + distance + "м. Максимум для собаки - " + MAX_RUN_DISTANCE + "м.");
        }
    }

    public void swim(int distance) {
        if (distance <= 0) {
            System.out.println("Дистанция должна быть положительной");
            return;
        }
        if (distance <= MAX_SWIM_DISTANCE) {
            System.out.println(name + " проплыл " + distance + "м.");
        } else {
            System.out.println(name + " не может проплыть " + distance + "м. Максимум для собаки - " + MAX_SWIM_DISTANCE + "м.");
        }
    }
}
