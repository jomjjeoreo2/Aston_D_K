public class Animal {
    private static int totalAnimals = 0;
    protected String name;

    public Animal(String name) {
        this.name = name;
        totalAnimals++;
    }
    public static int getTotalAnimals() {
        return totalAnimals;
    }
}
